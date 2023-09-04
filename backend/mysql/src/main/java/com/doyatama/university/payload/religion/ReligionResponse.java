package com.doyatama.university.payload.religion;

import java.time.Instant;

public class ReligionResponse {
    private Long id;
    private String name;
    private String description;
    private Instant updatedAt;
    private Instant createdAt;

    public ReligionResponse() {
    }

    public ReligionResponse(Long id, String name, String description, Instant updatedAt, Instant createdAt) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.updatedAt = updatedAt;
        this.createdAt = createdAt;
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
