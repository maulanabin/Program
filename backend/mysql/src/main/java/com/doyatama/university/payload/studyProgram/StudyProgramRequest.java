package com.doyatama.university.payload.studyProgram;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class StudyProgramRequest {
    @NotNull
    private Long department_id;
    @NotBlank
    private String name;

    @NotBlank
    private String description;

    public Long getDepartment_id() {
        return department_id;
    }

    public void setDepartment_id(Long department_id) {
        this.department_id = department_id;
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
