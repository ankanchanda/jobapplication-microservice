package org.ankanchanda.companyms.company.impl;

import jakarta.ws.rs.NotFoundException;
import org.ankanchanda.companyms.company.CompanyRepository;
import org.ankanchanda.companyms.company.CompanyService;
import org.ankanchanda.companyms.company.mapper.CompanyMapper;
import org.ankanchanda.companyms.dto.CompanyWithReviewsDTO;
import org.ankanchanda.companyms.dto.ReviewMessage;
import org.ankanchanda.companyms.external.Review;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.ankanchanda.companyms.clients.ReviewClient;
import org.ankanchanda.companyms.company.Company;

@Service
public class CompanyServiceImpl implements CompanyService{
    private final CompanyRepository companyRepository;
    private final ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
    }

    @Override
    @CircuitBreaker(name = "reviewBreaker", fallbackMethod = "reviewBreakerFallback") // should be same as mentioned in application.yml
    // for resilience4j
    public List<CompanyWithReviewsDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyWithReviewsDTO> companyWithReviewsDTOs = companies.stream()
                .map(this::getCompanyWithReviewsDTO)
                .collect(Collectors.toList());

        return companyWithReviewsDTOs;
    }

    public List<String> reviewBreakerFallback(Exception e) {
        return List.of("Review service is down, please try again later");
    }

    @Override
    public CompanyWithReviewsDTO findCompanyById(Long id) {
        Company company = companyRepository.findById(id).orElse(null);
        return company != null ? getCompanyWithReviewsDTO(company) : null;
    }

    @Override
    public void createCompany(Company company) {
        companyRepository.save(company);
    }

    @Override
    public boolean deleteCompanyById(Long id) {
        if(companyRepository.existsById(id)) {
            companyRepository.deleteById(id);
            return true;
        } 
        return false;
    }

    @Override
    public boolean updateCompany(Long id, Company updatedCompany) {
        Optional<Company> companyOptional = companyRepository.findById(id);
        if(companyOptional.isPresent()){
            Company company = companyOptional.get();
            company.setName(updatedCompany.getName());
            company.setDescription(updatedCompany.getDescription());
            companyRepository.save(company);
            return true;
        }
        return false;
    }

    @Override
    public void updateCompanyRating(ReviewMessage reviewMessage) {
        Company company = companyRepository.findById(
                reviewMessage.getCompanyId())
                .orElseThrow(() -> new NotFoundException("company not found")
        );
        double averageRating = reviewClient.getAverageRatingByCompanyId(company.getId());
        company.setRating(averageRating);
        companyRepository.save(company);
    }

    private  CompanyWithReviewsDTO getCompanyWithReviewsDTO(Company company) {
        List<Review> reviews = reviewClient.getReviewsByCompanyId(company.getId());
        CompanyWithReviewsDTO dto = CompanyMapper.mapToCompanyWithReviewsDTO(company, reviews);
        return dto;
    } 
}
