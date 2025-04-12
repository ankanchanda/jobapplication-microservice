package org.ankanchanda.companyms.company.mapper;

import java.util.List;

import org.ankanchanda.companyms.company.Company;
import org.ankanchanda.companyms.dto.CompanyWithReviewsDTO;
import org.ankanchanda.companyms.external.Review;

public class CompanyMapper {
    public static CompanyWithReviewsDTO mapToCompanyWithReviewsDTO (Company company, List<Review> reviews) {
        CompanyWithReviewsDTO companyWithReviewDTO = new CompanyWithReviewsDTO();
        companyWithReviewDTO.setId(company.getId());
        companyWithReviewDTO.setName(company.getName());
        companyWithReviewDTO.setDescription(company.getDescription());
        companyWithReviewDTO.setReviews(reviews);

        return companyWithReviewDTO;
    }
}
