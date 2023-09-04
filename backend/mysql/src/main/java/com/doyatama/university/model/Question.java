package com.doyatama.university.model;

import com.doyatama.university.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.BatchSize;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "questions")
public class Question extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @Size(max = 150)
    private String name;

    @Nullable
    @Lob
    private String description;

    @Nullable
    @Size(max = 150)
    private String answer_type;

    @Nullable
    @Size(max = 150)
    private String question_type;

    @Nullable
    @Size(max = 150)
    private String file_path;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rps_detail_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private DetailRps rps_detail;

    @OneToMany(
            mappedBy = "question",
            cascade = CascadeType.ALL,
            fetch = FetchType.EAGER,
            orphanRemoval = true
    )
    @Fetch(FetchMode.SELECT)
//    @BatchSize(size = 4)
    private List<Answer> answers = new ArrayList<>();

    public Question() {
    }

    public Question(Long id) {
        this.id = id;
    }

    public Question(Long id, String name, String description, String answer_type, String question_type, String file_path, DetailRps rps_detail, List<Answer> answers) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.answer_type = answer_type;
        this.question_type = question_type;
        this.file_path = file_path;
        this.rps_detail = rps_detail;
        this.answers = answers;
    }

    public Question(Long id, String name, String description, String answer_type, String question_type, String file_path, DetailRps rps_detail) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.answer_type = answer_type;
        this.question_type = question_type;
        this.file_path = file_path;
        this.rps_detail = rps_detail;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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

    public String getFilePath() {
        return file_path;
    }

    public void setFilePath(String file_path) {
        this.file_path = file_path;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

    public DetailRps getRps_detail() {
        return rps_detail;
    }

    public void setRps_detail(DetailRps rps_detail) {
        this.rps_detail = rps_detail;
    }
}
