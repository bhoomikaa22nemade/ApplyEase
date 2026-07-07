package com.applyease.backend.dto;

import java.util.List;

public class ApplyRequest {

    private String keyword;
    private String location;
    private String experience;
    private String portal;
    private List<String> jobIds;

    public String getKeyword() {
        return keyword;
    }

    public void setKeyword(String keyword) {
        this.keyword = keyword;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getPortal() {
        return portal;
    }

    public void setPortal(String portal) {
        this.portal = portal;
    }

    public List<String> getJobIds() {
        return jobIds;
    }

    public void setJobIds(List<String> jobIds) {
        this.jobIds = jobIds;
    }

}
