package com.doyatama.university.payload.question;

import javax.validation.constraints.NotBlank;

public class QuestionRequest {
    @NotBlank
    private Long rps_detail_id;

    @NotBlank
    private String name;

    @NotBlank
    private String description;

    @NotBlank
    private String answer_type;
    @NotBlank
    private String question_type;
    @NotBlank
    private String file_path;

    public QuestionRequest(Long rps_detail_id, String name, String description, String answer_type, String question_type) {
        this.rps_detail_id = rps_detail_id;
        this.name = name;
        this.description = description;
        this.answer_type = answer_type;
        this.question_type = question_type;
    }

    public Long getRps_detail_id() {
        return rps_detail_id;
    }

    public void setRps_detail_id(Long rps_detail_id) {
        this.rps_detail_id = rps_detail_id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getAnswerType() {
        return answer_type;
    }

    public void setAnswerType(String answer_type) {
        this.answer_type = answer_type;
    }

    public String getQuestionType() {
        return question_type;
    }

    public void setQuestionType(String question_type) {
        this.question_type = question_type;
    }

    public String getFile_path() {
        return file_path;
    }

    public void setFile_path(String file_path) {
        this.file_path = file_path;
    }
}
