package com.doyatama.university.model;

import com.doyatama.university.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.*;

@Entity
@Table(name = "rps")
public class Rps extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "subject_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Subject subject;

    @Nullable
    private Integer semester;

    @Nullable
    private Date build_date;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_dev_lectures",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private Set<Lecture> dev_lectures = new HashSet<>();

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "coordinator", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture coordinator;

    @Nullable
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "ka_study_program", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    private Lecture ka_study_program;

//    @NotBlank
    @Nullable
    @Lob
    private String cpl_study_program;

//    @NotBlank
    @Nullable
    @Lob
    private String cpl_subject;

//    @NotBlank
    @Nullable
    @Lob
    private String subject_description;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_learning_materials",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_material_id"))
    private Set<LearningMaterial> learning_materials = new HashSet<>();

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_references",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "reference_id"))
    private Set<Reference> references = new HashSet<>();

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_learning_medias",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "learning_media_id"))
    private Set<LearningMedia> learning_medias = new HashSet<>();

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_support_lectures",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "lecture_id"))
    private Set<Lecture> support_lectures = new HashSet<>();

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "rps_requirement_subjects",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "subject_id"))
    private Set<Subject> requirement_subjects = new HashSet<>();

    @OneToMany(mappedBy = "rps", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<DetailRps> detailRpsList = new ArrayList<>();

    public Rps() {
    }

    public Rps(Long id) {
        this.id = id;
    }

    public Rps(Long id, Subject subject, Integer semester, Date build_date, Set<Lecture> dev_lectures, Lecture coordinator, Lecture ka_study_program, String cpl_study_program, String cpl_subject, String subject_description, Set<LearningMaterial> learning_materials, Set<Reference> references, Set<LearningMedia> learning_medias, Set<Lecture> support_lectures, Set<Subject> requirement_subjects, List<DetailRps> detailRpsList) {
        this.id = id;
        this.subject = subject;
        this.semester = semester;
        this.build_date = build_date;
        this.dev_lectures = dev_lectures;
        this.coordinator = coordinator;
        this.ka_study_program = ka_study_program;
        this.cpl_study_program = cpl_study_program;
        this.cpl_subject = cpl_subject;
        this.subject_description = subject_description;
        this.learning_materials = learning_materials;
        this.references = references;
        this.learning_medias = learning_medias;
        this.support_lectures = support_lectures;
        this.requirement_subjects = requirement_subjects;
        this.detailRpsList = detailRpsList;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Date getBuild_date() {
        return build_date;
    }

    public void setBuild_date(Date build_date) {
        this.build_date = build_date;
    }

    public Set<Lecture> getDev_lectures() {
        return dev_lectures;
    }

    public void setDev_lectures(Set<Lecture> dev_lectures) {
        this.dev_lectures = dev_lectures;
    }

    public Lecture getCoordinator() {
        return coordinator;
    }

    public void setCoordinator(Lecture coordinator) {
        this.coordinator = coordinator;
    }

    public Lecture getKa_study_program() {
        return ka_study_program;
    }

    public void setKa_study_program(Lecture ka_study_program) {
        this.ka_study_program = ka_study_program;
    }

    public String getCpl_study_program() {
        return cpl_study_program;
    }

    public void setCpl_study_program(String cpl_study_program) {
        this.cpl_study_program = cpl_study_program;
    }

    public String getCpl_subject() {
        return cpl_subject;
    }

    public void setCpl_subject(String cpl_subject) {
        this.cpl_subject = cpl_subject;
    }

    public String getSubject_description() {
        return subject_description;
    }

    public void setSubject_description(String subject_description) {
        this.subject_description = subject_description;
    }

    public Set<LearningMaterial> getLearning_materials() {
        return learning_materials;
    }

    public void setLearning_materials(Set<LearningMaterial> learning_materials) {
        this.learning_materials = learning_materials;
    }

    public Set<Reference> getReferences() {
        return references;
    }

    public void setReferences(Set<Reference> references) {
        this.references = references;
    }

    public Set<LearningMedia> getLearning_medias() {
        return learning_medias;
    }

    public void setLearning_medias(Set<LearningMedia> learning_medias) {
        this.learning_medias = learning_medias;
    }

    public Set<Lecture> getSupport_lectures() {
        return support_lectures;
    }

    public void setSupport_lectures(Set<Lecture> support_lectures) {
        this.support_lectures = support_lectures;
    }

    public Set<Subject> getRequirement_subjects() {
        return requirement_subjects;
    }

    public void setRequirement_subjects(Set<Subject> requirement_subjects) {
        this.requirement_subjects = requirement_subjects;
    }

    public List<DetailRps> getDetailRpsList() {
        return detailRpsList;
    }

    public void setDetailRpsList(List<DetailRps> detailRpsList) {
        this.detailRpsList = detailRpsList;
    }
}
