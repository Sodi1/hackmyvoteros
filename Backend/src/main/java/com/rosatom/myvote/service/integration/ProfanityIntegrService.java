package com.rosatom.myvote.service.integration;

import com.rosatom.myvote.model.dto.AnalyticsEmotionalResponse;
import com.rosatom.myvote.model.dto.AnalyticsRequest;
import com.rosatom.myvote.model.dto.ProfanityEntry;
import com.rosatom.myvote.model.dto.ProfanityResponse;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.util.List;

@FeignClient(value = "profanity", url = "${integration.profanity.url}")
public interface ProfanityIntegrService {
    @RequestMapping(method = RequestMethod.POST, value = "/censor-words/batch", consumes = "application/json")
    List<ProfanityEntry> getCensored(@RequestBody AnalyticsRequest request);
}
