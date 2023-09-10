package com.rosatom.myvote.service;

import com.rosatom.myvote.model.enums.Emotional;
import com.rosatom.myvote.model.enums.Order;
import com.rosatom.myvote.repository.QuestionRepository;
import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
@Service
public class AnalyticsService {
    private final QuestionRepository questionRepository;
    private final EntityManager entityManager;


    public List<Map<String, Integer>> topPositiveQuestion(Long fileId, Emotional emotional, Integer limit) {
        return questionRepository.findTopAnalytics(fileId, emotional.toString(), limit);
    }

    public List<Map<String, Object>> emotionalDistribution(Long fileId, Integer limit, Order numberOrder, Long questionId) {
        int orderByColumn = numberOrder.getOrder();

        String sqlQuery = "SELECT * FROM (SELECT * FROM (SELECT\s" +
                "    src.name," +
                "    ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'neutral' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'neutral'  AND a2.file_id = :file_id) * 1.0) * 100.0, 2) neutral," +
                "    ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'positive' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'positive'  AND a2.file_id = :file_id) * 1.0) * 100.0, 2) positive," +
                "    ROUND(((SELECT COUNT(*) FROM answer a1 WHERE a1.external_id = src.external_id AND a1.emotional = 'negative' AND a1.file_id = :file_id) * 1.0 / (SELECT COUNT(*) FROM answer a2 WHERE a2.emotional = 'negative' AND a2.file_id = :file_id) * 1.0) * 100.0, 2) negative," +
                "    (SELECT COUNT(*) FROM answer a1 WHERE a1.file_id = :file_id AND a1.external_id = src.external_id)" +
                "FROM (" +
                "    SELECT DISTINCT q.external_id, q.name FROM question q" +
                "    WHERE q.file_id = :file_id ";
            if (questionId != null){
                sqlQuery += "AND (:question_id IS NULL OR q.id = :question_id) ";
            }
            sqlQuery +=  ") src) src" +
                    " ORDER BY " + orderByColumn +
                    " DESC) src LIMIT :limit";
        List<Map<String, Object>> resultList = new ArrayList<>();

        var query = entityManager
                .createNativeQuery(sqlQuery)
                .setParameter("file_id", fileId)
                .setParameter("limit", limit);
        if (questionId != null){
            query.setParameter("question_id", questionId);
        }

        List<Object[]> queryResultList = query.getResultList();

        for (Object[] row : queryResultList) {
            Map<String, Object> rowMap = new HashMap<>();
            rowMap.put("name", row[0].toString());
            rowMap.put("neutral", ((Number) row[1]).doubleValue());
            rowMap.put("positive", ((Number) row[2]).doubleValue());
            rowMap.put("negative", ((Number) row[3]).doubleValue());
            rowMap.put("count", ((Number) row[4]).doubleValue());

            resultList.add(rowMap);
        }
        return resultList;
    }

    public List<Map<String, Integer>> count(Long fileId, Long questionId) {
        return questionRepository.countStat(fileId, questionId);
    }

    public Integer censoredStat(Long fileId, Long questionId) {
        return questionRepository.censoredStat(fileId, questionId);
    }

    public Integer countIncorrectAnswer(Long fileId, Long questionId) {
        return questionRepository.countIncorrectAnswer(fileId, questionId);
    }

    public List<Map<String, Integer>> incorrectAnswerTop(Long fileId, Double percent, Integer limit) {
        return questionRepository.incorrectAnswerTop(fileId, percent, limit);
    }

    public List<String> topAnswer(Long fileId, Long questionId, Integer limit) {
        return questionRepository.topAnswer(fileId, questionId, limit);
    }
}
