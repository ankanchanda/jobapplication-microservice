package org.ankanchanda.companyms.dto;

import java.util.List;

import org.ankanchanda.companyms.external.Review;

public class CompanyWithReviewsDTO {
    
    private Long id;
    private String name;
    private String description;
    List<Review> reviews;

    public CompanyWithReviewsDTO() {
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
    public List<Review> getReviews() {
        return reviews;
    }
    public void setReviews(List<Review> reviews) {
        this.reviews = reviews;
    }
}
