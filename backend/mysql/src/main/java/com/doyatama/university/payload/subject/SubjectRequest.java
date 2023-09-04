package com.doyatama.university.payload.subject;

import javax.validation.constraints.NotBlank;

public class SubjectRequest {
    @NotBlank
    private Long study_program_id;
    @NotBlank
    private Long subject_group_id;
    @NotBlank
    private String name;
    @NotBlank
    private String description;

    public Long getStudy_program_id() {
        return study_program_id;
    }

    public void setStudy_program_id(Long study_program_id) {
        this.study_program_id = study_program_id;
    }

    public Long getSubject_group_id() {
        return subject_group_id;
    }

    public void setSubject_group_id(Long subject_group_id) {
        this.subject_group_id = subject_group_id;
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
}
