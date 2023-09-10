package com.rosatom.myvote.service.langchecker;

import com.rosatom.myvote.model.dto.LangCheckerRequest;
import com.rosatom.myvote.model.dto.LangCheckerResponse;
import org.springframework.stereotype.Service;

@Service
public class LangCheckerService {

    private static Tokenizer tokenizer = LangSwitcherTokenizer.create();

    public LangCheckerResponse tokenize(LangCheckerRequest request){
        if (request == null || request.getMessage() == null) {
            return new LangCheckerResponse();
        }
        var langCheckerResponse = new LangCheckerResponse();
        var tokenizerResponse = tokenizer.tokenize(request.getMessage());
        langCheckerResponse.setMessage(tokenizerResponse.toString());
        langCheckerResponse.setTokens(tokenizerResponse.tokens());
        return langCheckerResponse;
    }
}
