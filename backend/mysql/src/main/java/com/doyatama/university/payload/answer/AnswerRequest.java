package com.doyatama.university.payload.answer;

import javax.validation.constraints.NotBlank;

public class AnswerRequest {
    @NotBlank
    private Long question_id;

    @NotBlank
    private String description;

    @NotBlank
    private String explanation;

    @NotBlank
    private Boolean is_right;

    public Long getQuestion_id() {
        return question_id;
    }

    public String getDescription() {
        return description;
    }

    public String getExplanation() {
        return explanation;
    }

    public Boolean getIs_right() {
        return is_right;
    }
}
