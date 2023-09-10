package com.rosatom.myvote.model.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.Data;

@Data
@Entity(name = "answer")
public class AnswerEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(name = "external_id")
    private Long externalId;
    @Column(name = "count_answer")
    private Integer count;
    @Column(name = "sentiment")
    private String sentiment;
    @Column(name = "cluster")
    private String cluster;
    @Column(name = "answer")
    private String answer;
    @Column(name = "answer_censored")
    private String answerCensored;
    @Column(name = "bad_words_percent")
    private Double badWordsPercent;
    @Column(name = "emotional")
    private String emotional;
    @Column(name = "file_id")
    private Long fileId;
    @Column(name = "significance")
    private Double significance;
}
