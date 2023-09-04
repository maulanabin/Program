package com.doyatama.university.payload.lecture;

import org.springframework.lang.Nullable;
import javax.validation.constraints.NotBlank;
import java.util.Date;

public class LectureRequest {
    @NotBlank
    private Long user_id;

    @Nullable
    private Long nidn;

    @NotBlank
    private String name;

    @Nullable
    private String place_born;

    @Nullable
    private Date date_born;

    @Nullable
    private Boolean gender;

    @NotBlank
    private Long religion_id;

    @Nullable
    private String phone;

    @NotBlank
    private String address;

    @Nullable
    private Boolean status;

    public Long getUser_id() {
        return user_id;
    }

    public void setUser_id(Long user_id) {
        this.user_id = user_id;
    }

    @Nullable
    public Long getNidn() {
        return nidn;
    }

    public void setNidn(@Nullable Long nidn) {
        this.nidn = nidn;
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

    public Long getReligion_id() {
        return religion_id;
    }

    public void setReligion_id(Long religion_id) {
        this.religion_id = religion_id;
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
