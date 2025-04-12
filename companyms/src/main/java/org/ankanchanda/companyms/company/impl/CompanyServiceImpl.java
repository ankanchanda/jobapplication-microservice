package org.ankanchanda.companyms.company.impl;

import org.ankanchanda.companyms.company.CompanyRepository;
import org.ankanchanda.companyms.company.CompanyService;
import org.ankanchanda.companyms.company.mapper.CompanyMapper;
import org.ankanchanda.companyms.dto.CompanyWithReviewsDTO;
import org.ankanchanda.companyms.external.Review;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.ankanchanda.companyms.company.Company;

@Service
public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;
    private final String reviewServiceURL = "http://REVIEWMS:8083/reviews";
    @Autowired
    RestTemplate restTemplate;

    public CompanyServiceImpl(CompanyRepository companyRepository) {
        this.companyRepository = companyRepository;
    }

    @Override
    public List<CompanyWithReviewsDTO> findAll() {
        List<Company> companies = companyRepository.findAll();
        List<CompanyWithReviewsDTO> companyWithReviewsDTOs = companies.stream()
                .map(this::getCompanyWithReviewsDTO)
                .collect(Collectors.toList());

        return companyWithReviewsDTOs;
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

    private  CompanyWithReviewsDTO getCompanyWithReviewsDTO(Company company) {
        String urlWithParams = UriComponentsBuilder.fromUriString(reviewServiceURL)
                .queryParam("companyId", company.getId())
                .toUriString();
        ResponseEntity<List<Review>> response = restTemplate.exchange(
            urlWithParams,
            HttpMethod.GET,
            null,
            new ParameterizedTypeReference<List<Review>>() {}
        );
        List<Review> reviews = response.getBody();
        CompanyWithReviewsDTO dto = CompanyMapper.mapToCompanyWithReviewsDTO(company, reviews);
        return dto;
    } 
}
