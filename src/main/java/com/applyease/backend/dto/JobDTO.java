package com.applyease.backend.dto;

public class JobDTO {

    private Long id;

    private String title;

    private String company;

    private String location;

    private String experience;

    private String salary;

    private String jobType;

    private String skills;

    private String description;

    private String postedDate;

    public JobDTO() {
    }

    public JobDTO(
            Long id,
            String title,
            String company,
            String location,
            String experience,
            String salary,
            String jobType,
            String skills,
            String description,
            String postedDate) {

        this.id = id;
        this.title = title;
        this.company = company;
        this.location = location;
        this.experience = experience;
        this.salary = salary;
        this.jobType = jobType;
        this.skills = skills;
        this.description = description;
        this.postedDate = postedDate;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
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

    public String getSalary() {
        return salary;
    }

    public void setSalary(String salary) {
        this.salary = salary;
    }

    public String getJobType() {
        return jobType;
    }

    public void setJobType(String jobType) {
        this.jobType = jobType;
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPostedDate() {
        return postedDate;
    }

    public void setPostedDate(String postedDate) {
        this.postedDate = postedDate;
    }

}