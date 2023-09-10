package com.rosatom.myvote.service;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.rosatom.myvote.model.dto.AnalyticsRequest;
import com.rosatom.myvote.model.dto.AnalyticsEmotionalResponse;
import com.rosatom.myvote.model.dto.ProfanityResponse;
import com.rosatom.myvote.model.dto.SentenceDto;
import com.rosatom.myvote.model.dto.SentenceResponse;
import com.rosatom.myvote.model.dto.SpellCheckDto;
import com.rosatom.myvote.model.dto.SpellCheckResponseDto;
import com.rosatom.myvote.model.entity.AnswerEntity;
import com.rosatom.myvote.model.entity.FileEntity;
import com.rosatom.myvote.model.entity.QuestionEntity;
import com.rosatom.myvote.repository.AnswerRepository;
import com.rosatom.myvote.repository.QuestionRepository;
import com.rosatom.myvote.service.integration.AnalyticsIntegrationService;
import com.rosatom.myvote.service.integration.ProfanityIntegrService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;
import java.util.zip.ZipFile;
import java.util.zip.ZipEntry;


@RequiredArgsConstructor
@Service
@Slf4j
public class LoadDataService {
    private final AnswerRepository answerRepository;
    private final QuestionRepository questionRepository;
    private final FileService fileService;
    private final AnalyticsIntegrationService analyticsIntegrationService;
    private final ProfanityIntegrService profanityIntegrService;
    private ObjectMapper objectMapper = new ObjectMapper();


    public void save(MultipartFile file) {
        try {
            File tempDir = new File(System.getProperty("java.io.tmpdir") + UUID.randomUUID().toString());
            tempDir.mkdirs();

            File zipFile = new File(tempDir, file.getOriginalFilename());
            file.transferTo(zipFile);

            FileEntity newFileEntity = unzip(zipFile, tempDir);

            processDirectory(newFileEntity.getId(), tempDir.getPath());

            deleteDirectory(tempDir);
            zipFile.delete();
        } catch (Exception e) {
            log.error(e.fillInStackTrace().toString());
        }
    }

    private FileEntity unzip(File zipFile, File outputDir) throws Exception {
        FileEntity newFileEntity = null;
        try (ZipFile zip = new ZipFile(zipFile)) {
            String nameZip = zip.getName();
            Path path = Paths.get(nameZip);
            var fileEntity = new FileEntity();
            fileEntity.setCreatedAt(LocalDateTime.now());
            fileEntity.setName(path.getFileName().toString());
            newFileEntity = fileService.save(fileEntity);
            Enumeration<? extends ZipEntry> entries = zip.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = entries.nextElement();
                File entryFile = new File(outputDir, entry.getName());
                if (entry.getName().contains("__MACOSX") || entry.getName().contains("._")) {
                    continue;
                }
                if (entry.isDirectory()) {
                    entryFile.mkdirs();
                } else {
                    try (InputStream is = zip.getInputStream(entry);
                         OutputStream os = new FileOutputStream(entryFile)) {
                        byte[] buffer = new byte[4096];
                        int bytesRead;
                        while ((bytesRead = is.read(buffer)) != -1) {
                            os.write(buffer, 0, bytesRead);
                        }
                    }
                }
            }
        }
        return newFileEntity;
    }

    private void processDirectory(Long fileId, String dirPath) throws Exception {
        File dir = new File(dirPath);
        processFilesInDirectory(fileId, dir);
    }

    private void processFilesInDirectory(Long fileId, File dir) throws Exception {
        File[] files = dir.listFiles();
        if (files != null) {
            for (File file : files) {
                if (file.isDirectory()) {
                    // Если это директория, вызываем рекурсивно этот метод
                    processFilesInDirectory(fileId, file);
                } else if (file.getName().toLowerCase().endsWith(".json")) {
                    // Если это JSON файл, обрабатываем его
                    processJsonFile(fileId, file);
                }
            }
        }
    }


    private void processJsonFile(Long fileId, File jsonFile) throws Exception {
        JsonNode rootNode = objectMapper.readTree(jsonFile);

        String question = Optional.ofNullable(rootNode.get("question")).map(JsonNode::asText).orElse(null);
        long id = Optional.ofNullable(rootNode.get("id")).map(JsonNode::asLong).orElse(null);
        List<AnswerEntity> answerEntities = new ArrayList<>();
        JsonNode answersNode = rootNode.get("answers");
        if (answersNode != null && answersNode.isArray()) {
            for (JsonNode answerNode : answersNode) {
                String answer = Optional.ofNullable(answerNode.get("answer")).map(JsonNode::asText).orElse(null);
                Integer count = Optional.ofNullable(answerNode.get("count")).map(JsonNode::asInt).orElse(null);
                String cluster = Optional.ofNullable(answerNode.get("cluster")).map(JsonNode::asText).orElse(null);
                String sentiment = Optional.ofNullable(answerNode.get("sentiment")).map(JsonNode::asText).orElse(null);
                var answerEntitie = new AnswerEntity();
                answerEntitie.setExternalId(id);
                answerEntitie.setAnswer(answer);
                answerEntitie.setCount(count);
                answerEntitie.setCluster(cluster);
                answerEntitie.setSentiment(sentiment);
                answerEntitie.setFileId(fileId);
                answerEntities.add(answerEntitie);
            }
        }
        if (question != null) {
            var questionEntity = new QuestionEntity();
            questionEntity.setExternalId(id);
            questionEntity.setName(question);
            questionEntity.setFileId(fileId);
            questionRepository.save(questionEntity);
        }
        if (!answerEntities.isEmpty()) {

            int batchSize = 100; // Размер пакета

            for (int i = 0; i < answerEntities.size(); i += batchSize) {
                int endIndex = Math.min(i + batchSize, answerEntities.size());
                List<AnswerEntity> batch = answerEntities.subList(i, endIndex);

                List<String> texts = new ArrayList<>();
                for (AnswerEntity entity : batch) {
                    texts.add(entity.getAnswer());
                }

                var request = new AnalyticsRequest();
                request.setTexts(texts);

                AnalyticsEmotionalResponse response = analyticsIntegrationService.getEmotional(request);
                var profanityEntries = profanityIntegrService.getCensored(request);

                for (int j = 0; j < batch.size(); j++) {
                    var entity = batch.get(j);
                    entity.setEmotional(response.getEmotions().get(j).get(0).toString());
                    entity.setAnswerCensored(profanityEntries.get(j).getCensored());
                    entity.setBadWordsPercent(profanityEntries.get(j).getBadWordsPercent());
                }
            }
            var sentenceDto = new SentenceDto();
            sentenceDto.setSourceSentence(question);
            List<String> answers = answerEntities.stream().map(AnswerEntity::getAnswer).collect(Collectors.toList());
            sentenceDto.setSentences(answers);
            SentenceResponse sentenceResponse = analyticsIntegrationService.getSentence(sentenceDto);

            var spellCheckReq = new SpellCheckDto();
            spellCheckReq.setTexts(answers);

            SpellCheckResponseDto spellCheckResponseDto = analyticsIntegrationService.spellCheck(spellCheckReq);
            var sentences = sentenceResponse.getSentences();
            var spells = spellCheckResponseDto.getSpells();
            for (int i = 0; i < answerEntities.size(); i++) {
                answerEntities.get(i).setSignificance((Double)sentences.get(i).get(0));
                answerEntities.get(i).setAnswer(spells.get(i));
            }
            answerRepository.saveAll(answerEntities);
        }
    }

    private void deleteDirectory(File directory) {
        if (directory.exists()) {
            File[] files = directory.listFiles();
            if (files != null) {
                for (File file : files) {
                    if (file.isDirectory()) {
                        deleteDirectory(file);
                    } else {
                        file.delete();
                    }
                }
            }
            directory.delete();
        }
    }
}
