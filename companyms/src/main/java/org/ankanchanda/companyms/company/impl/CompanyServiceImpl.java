package org.ankanchanda.companyms.company.impl;

import org.ankanchanda.companyms.company.CompanyRepository;
import org.ankanchanda.companyms.company.CompanyService;
import org.ankanchanda.companyms.company.mapper.CompanyMapper;
import org.ankanchanda.companyms.dto.CompanyWithReviewsDTO;
import org.ankanchanda.companyms.external.Review;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
import java.util.Optional;

import org.ankanchanda.companyms.clients.ReviewClient;
import org.ankanchanda.companyms.company.Company;

@Service
public class CompanyServiceImpl implements CompanyService{
    private CompanyRepository companyRepository;
    private ReviewClient reviewClient;

    public CompanyServiceImpl(CompanyRepository companyRepository, ReviewClient reviewClient) {
        this.companyRepository = companyRepository;
        this.reviewClient = reviewClient;
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
        List<Review> reviews = reviewClient.getReviewsByCompanyId(company.getId());
        CompanyWithReviewsDTO dto = CompanyMapper.mapToCompanyWithReviewsDTO(company, reviews);
        return dto;
    } 
}
