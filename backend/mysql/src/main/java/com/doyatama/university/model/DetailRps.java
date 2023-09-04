package com.doyatama.university.model;

import com.doyatama.university.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name = "detail_rps")
public class DetailRps extends UserDateAudit {
    @Id
    @Column(name = "id")
    private Long id;

    @ManyToOne
    @JoinColumn(name = "rps_id", insertable = false, updatable = false)
    private Rps rps;

    @Nullable
    private Integer week;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "detail_rps_sub_cp_subjects",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_cp_subject_id"))
    private Set<SubCpSubject> subCpSubjects = new HashSet<>();

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "detail_rps_sub_learning_materials",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "sub_learning_material_id"))
    private Set<SubLearningMaterial> subLearningMaterials = new HashSet<>();

//    @NotBlank
    @Nullable
    private String estimated_time;

//    @NotBlank
    @Nullable
    @Lob
    private String learning_experience;

//    @NotBlank
    @Nullable
    @Lob
    private String criteria;

    @Nullable
    @ManyToMany(fetch = FetchType.LAZY)
    @JoinTable(name = "detail_rps_appraisal_forms",
            joinColumns = @JoinColumn(name = "rps_id"),
            inverseJoinColumns = @JoinColumn(name = "appraisal_form_id"))
    private Set<AppraisalForm> appraisalForms = new HashSet<>();

//    @NotBlank
    @Lob
    @Nullable
    private String assessment_indicators;

    @Nullable
    private Double rating_weight;

    public DetailRps() {
    }

    public DetailRps(Long id) {
        this.id = id;
    }

    public DetailRps(Long id, Rps rps) {
        this.id = id; this.rps = rps;
    }

    public DetailRps(Long id, Rps rps, Integer week, Set<SubCpSubject> subCpSubjects, Set<SubLearningMaterial> subLearningMaterials, String estimated_time, String learning_experience, String criteria, Set<AppraisalForm> appraisalForms, String assessment_indicators, Double rating_weight) {
        this.id = id;
        this.rps = rps;
        this.week = week;
        this.subCpSubjects = subCpSubjects;
        this.subLearningMaterials = subLearningMaterials;
        this.estimated_time = estimated_time;
        this.learning_experience = learning_experience;
        this.criteria = criteria;
        this.appraisalForms = appraisalForms;
        this.assessment_indicators = assessment_indicators;
        this.rating_weight = rating_weight;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Rps getRps() {
        return rps;
    }

    public void setRps(Rps rps) {
        this.rps = rps;
    }

    public Integer getWeek() {
        return week;
    }

    public void setWeek(Integer week) {
        this.week = week;
    }

    public Set<SubCpSubject> getSubCpSubjects() {
        return subCpSubjects;
    }

    public void setSubCpSubjects(Set<SubCpSubject> subCpSubjects) {
        this.subCpSubjects = subCpSubjects;
    }

    public Set<SubLearningMaterial> getSubLearningMaterials() {
        return subLearningMaterials;
    }

    public void setSubLearningMaterials(Set<SubLearningMaterial> subLearningMaterials) {
        this.subLearningMaterials = subLearningMaterials;
    }

    public String getEstimated_time() {
        return estimated_time;
    }

    public void setEstimated_time(String estimated_time) {
        this.estimated_time = estimated_time;
    }

    public String getLearning_experience() {
        return learning_experience;
    }

    public void setLearning_experience(String learning_experience) {
        this.learning_experience = learning_experience;
    }

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }

    public Set<AppraisalForm> getAppraisalForms() {
        return appraisalForms;
    }

    public void setAppraisalForms(Set<AppraisalForm> appraisalForms) {
        this.appraisalForms = appraisalForms;
    }

    public String getAssessment_indicators() {
        return assessment_indicators;
    }

    public void setAssessment_indicators(String assessment_indicators) {
        this.assessment_indicators = assessment_indicators;
    }

    public Double getRating_weight() {
        return rating_weight;
    }

    public void setRating_weight(Double rating_weight) {
        this.rating_weight = rating_weight;
    }
}
