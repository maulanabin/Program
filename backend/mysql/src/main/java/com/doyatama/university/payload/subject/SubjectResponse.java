package com.doyatama.university.payload.subject;

import com.doyatama.university.payload.studyProgram.StudyProgramResponse;
import com.doyatama.university.payload.studyProgram.StudyProgramWithoutDepartmentResponse;
import com.doyatama.university.payload.subjectGroup.SubjectGroupResponse;

import javax.validation.constraints.NotBlank;
import java.time.Instant;

public class SubjectResponse {
    private Long id;
    private StudyProgramWithoutDepartmentResponse study_program;
    private SubjectGroupResponse subject_group;
    private String name;
    private String description;
    private Instant updatedAt;
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public StudyProgramWithoutDepartmentResponse getStudy_program() {
        return study_program;
    }

    public void setStudy_program(StudyProgramWithoutDepartmentResponse study_program) {
        this.study_program = study_program;
    }

    public SubjectGroupResponse getSubject_group() {
        return subject_group;
    }

    public void setSubject_group(SubjectGroupResponse subject_group) {
        this.subject_group = subject_group;
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

    public Instant getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Instant updatedAt) {
        this.updatedAt = updatedAt;
    }

    public Instant getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Instant createdAt) {
        this.createdAt = createdAt;
    }
}
