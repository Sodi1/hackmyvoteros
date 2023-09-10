package com.rosatom.myvote.controller;

import com.rosatom.myvote.model.dto.EmotionalPercentageByQuestion;
import com.rosatom.myvote.model.enums.Emotional;
import com.rosatom.myvote.model.enums.Order;
import com.rosatom.myvote.service.AnalyticsService;
import com.rosatom.myvote.utils.WebUtils;
import lombok.RequiredArgsConstructor;
import org.bouncycastle.cert.ocsp.Req;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;
import java.util.Objects;

@RequiredArgsConstructor
@RestController
@RequestMapping(WebUtils.API_VERSION_V1 + "/analytics")
public class AnalyticController {

    private final AnalyticsService analyticsService;

    @GetMapping("/question/top-positive")
    public ResponseEntity<List<Map<String, Integer>>> topPositiveQuestion(@RequestParam("file_id") Long fileId,
                                                                          @RequestParam("emotional") Emotional emotional,
                                                                          @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(analyticsService.topPositiveQuestion(fileId, emotional, limit));
    }

    @GetMapping("/answer/emotional-distribution")
    public ResponseEntity<List<Map<String, Object>>> emotionalDistribution(@RequestParam("file_id") Long fileId,
                                                                           @RequestParam("limit") Integer limit,
                                                                           @RequestParam("number_order") Order numberOrder,
                                                                           @RequestParam(value = "question_id", required = false) Long questionId) {
        return ResponseEntity.ok(analyticsService.emotionalDistribution(fileId, limit, numberOrder, questionId));
    }

    @GetMapping("/question/count")
    public ResponseEntity<List<Map<String, Integer>>> count(@RequestParam("file_id") Long fileId,
                                                            @RequestParam(value = "question_id", required = false) Long questionId) {
        return ResponseEntity.ok(analyticsService.count(fileId, questionId));
    }

    @GetMapping("/censored/stat")
    public ResponseEntity<Integer> censoredStat(@RequestParam("file_id") Long fileId,
                                                @RequestParam(value = "question_id", required = false) Long questionId) {
        return ResponseEntity.ok(analyticsService.censoredStat(fileId, questionId));
    }

    @GetMapping("/answer/incorrect-answer")
    public ResponseEntity<Integer> countIncorrectAnswer(@RequestParam("file_id") Long fileId, @RequestParam(value = "question_id", required = false) Long questionId) {
        return ResponseEntity.ok(analyticsService.countIncorrectAnswer(fileId, questionId));
    }

    @GetMapping("/answer/incorrect-answer-top")
    public ResponseEntity<List<Map<String, Integer>>> incorrectAnswerTop(@RequestParam("file_id") Long fileId,
                                                                         @RequestParam("percent") Double percent,
                                                                         @RequestParam("limit") Integer limit) {
        return ResponseEntity.ok(analyticsService.incorrectAnswerTop(fileId, percent, limit));
    }

    @GetMapping("/answer/top")
    public ResponseEntity<List<String>> topAnswer(@RequestParam("file_id") Long fileId,
                                                  @RequestParam("limit") Integer limit,
                                                  @RequestParam(value = "question_id", required = false) Long questionId) {
        return ResponseEntity.ok(analyticsService.topAnswer(fileId, questionId, limit));
    }
}
