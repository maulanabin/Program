package com.doyatama.university.payload.lecture;

import com.doyatama.university.model.Religion;
import com.doyatama.university.payload.religion.ReligionResponse;
import org.springframework.lang.Nullable;

import javax.validation.constraints.NotBlank;
import java.time.Instant;
import java.util.Date;

public class LectureResponse {
    private Long id;
    private Long user_id;
    private Long nidn;
    private String name;
    private String place_born;
    private Date date_born;
    private Boolean gender;
    private ReligionResponse religion;
    private String phone;
    private String address;
    private Boolean status;
    private Instant updatedAt;
    private Instant createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    public Long getNidn() {
        return nidn;
    }

    public void setNidn(Long nidn) {
        this.nidn = nidn;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPlace_born() {
        return place_born;
    }

    public void setPlace_born(String place_born) {
        this.place_born = place_born;
    }

    public Date getDate_born() {
        return date_born;
    }

    public void setDate_born(Date date_born) {
        this.date_born = date_born;
    }

    public Boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public ReligionResponse getReligion() {
        return religion;
    }

    public void setReligion(ReligionResponse religion) {
        this.religion = religion;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
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
