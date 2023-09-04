package com.doyatama.university.model;

import com.doyatama.university.model.audit.UserDateAudit;
import com.fasterxml.jackson.annotation.JsonIgnore;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;
import org.springframework.lang.Nullable;

import javax.persistence.*;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.Date;

@Entity
@Table(name = "students")
public class Student extends UserDateAudit {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private User user;

    @Nullable
    private Long nim;

    @NotBlank
    @Size(max = 100)
    private String name;

    @Nullable
    @Size(max = 50)
    private String place_born;

    @Nullable
    private Date date_born;

    @Nullable
    private Boolean gender;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "religion_id", nullable = false)
    @OnDelete(action = OnDeleteAction.CASCADE)
    @JsonIgnore
    private Religion religion;

    @Nullable
    @Size(max = 15)
    private String phone;

    @NotBlank
    @Lob
    private String address;

    @Nullable
    private Boolean status;

    public Student() {
    }

    public Student(Long id) {
        this.id = id;
    }

    public Student(Long id, User user, @Nullable Long nim, String name, @Nullable String place_born, @Nullable Date date_born, @Nullable Boolean gender, Religion religion, @Nullable String phone, String address, @Nullable Boolean status) {
        this.id = id;
        this.user = user;
        this.nim = nim;
        this.name = name;
        this.place_born = place_born;
        this.date_born = date_born;
        this.gender = gender;
        this.religion = religion;
        this.phone = phone;
        this.address = address;
        this.status = status;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Nullable
    public Long getNim() {
        return nim;
    }

    public void setNim(@Nullable Long nim) {
        this.nim = nim;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Nullable
    public String getPlace_born() {
        return place_born;
    }

    public void setPlace_born(@Nullable String place_born) {
        this.place_born = place_born;
    }

    @Nullable
    public Date getDate_born() {
        return date_born;
    }

    public void setDate_born(@Nullable Date date_born) {
        this.date_born = date_born;
    }

    @Nullable
    public Boolean getGender() {
        return gender;
    }

    public void setGender(@Nullable Boolean gender) {
        this.gender = gender;
    }

    public Religion getReligion() {
        return religion;
    }

    public void setReligion(Religion religion) {
        this.religion = religion;
    }

    @Nullable
    public String getPhone() {
        return phone;
    }

    public void setPhone(@Nullable String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    @Nullable
    public Boolean getStatus() {
        return status;
    }

    public void setStatus(@Nullable Boolean status) {
        this.status = status;
    }
}
