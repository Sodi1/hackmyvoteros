package com.rosatom.myvote;

import com.rosatom.myvote.service.langchecker.LangSwitcherTokenizer;
import com.rosatom.myvote.service.langchecker.Tokenizer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication
@EnableJpaRepositories
@EnableFeignClients
public class Application  {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
        Tokenizer tokenizer = LangSwitcherTokenizer.create();
    }

}
