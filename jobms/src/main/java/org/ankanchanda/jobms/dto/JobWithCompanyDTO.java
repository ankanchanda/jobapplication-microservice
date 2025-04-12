package org.ankanchanda.jobms.dto;

import org.ankanchanda.jobms.external.Company;

public class JobWithCompanyDTO {

    private Long id;
    private String title;
    private String description;
    private String location;
    private Double minimumSalary;
    private Double maximumSalary;
    private Company company;

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
    public String getDescription() {
        return description;
    }
    public void setDescription(String description) {
        this.description = description;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Double getMinimumSalary() {
        return minimumSalary;
    }
    public void setMinimumSalary(Double minimumSalary) {
        this.minimumSalary = minimumSalary;
    }
    public Double getMaximumSalary() {
        return maximumSalary;
    }
    public void setMaximumSalary(Double maximumSalary) {
        this.maximumSalary = maximumSalary;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }
}
