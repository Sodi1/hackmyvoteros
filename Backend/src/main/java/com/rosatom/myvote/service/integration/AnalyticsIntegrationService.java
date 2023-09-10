package com.rosatom.myvote.service.integration;

import com.rosatom.myvote.model.dto.AnalyticsRequest;
import com.rosatom.myvote.model.dto.AnalyticsEmotionalResponse;
import com.rosatom.myvote.model.dto.SentenceDto;
import com.rosatom.myvote.model.dto.SentenceResponse;
import com.rosatom.myvote.model.dto.SpellCheckDto;
import com.rosatom.myvote.model.dto.SpellCheckResponseDto;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@FeignClient(value = "analytics", url = "${integration.analytics.url}")
public interface AnalyticsIntegrationService {
    @RequestMapping(method = RequestMethod.POST, value = "/api/emotions/", consumes = "application/json")
    AnalyticsEmotionalResponse getEmotional(@RequestBody AnalyticsRequest request);

    @RequestMapping(method = RequestMethod.POST, value = "/api/similarity-sentences/", consumes = "application/json")
    SentenceResponse getSentence(@RequestBody SentenceDto request);


    @RequestMapping(method = RequestMethod.POST, value = "/api/spellcheck/", consumes = "application/json")
    SpellCheckResponseDto spellCheck(@RequestBody SpellCheckDto spellCheckDto);

}
