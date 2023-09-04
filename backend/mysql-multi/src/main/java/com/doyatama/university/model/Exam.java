package com.doyatama.university.model;

import com.doyatama.university.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.*;

import javax.persistence.*;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

@Entity
@Table(name = "exams")
public class Exam extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "rps_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Subject rps;

    @NotBlank
    @Size(max = 150)
    private String nama;

    @NotBlank
    private Integer jml;

    public Exam() {
    }

    public Exam(Long id, Subject subject, String nama, Integer jml) {
        this.id = id;
        this.nama = nama;
        this.jml = jml;
    }
}
