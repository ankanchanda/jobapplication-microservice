package org.ankanchanda.jobms.job.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ankanchanda.jobms.dto.JobWithCompanyDTO;
import org.ankanchanda.jobms.external.Company;
import org.ankanchanda.jobms.job.Job;
import org.ankanchanda.jobms.job.JobRepository;
import org.ankanchanda.jobms.job.JobService;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private RestTemplate restTemplate;
    private String companyServiceUrl = "http://localhost:8081/companies";

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
        this.restTemplate = new RestTemplate();
    }

    @Override
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = jobs.stream()
                .map(this::geJobWithCompanyDTO)
                .collect(Collectors.toList());
        return jobWithCompanyDTOs;
    }

    @Override
    public Job findJobById(Long id) {
        return jobRepository.findById(id).orElse(null);
    }

    @Override
    public void createJob(Job job) {
        jobRepository.save(job);
    }

    @Override
    public boolean deleteJob(Long id) {
        try {
            jobRepository.deleteById(id);
            return true;

        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public boolean updateJob(Long id, Job updatedJob) {
        Optional<Job> jobOptional = jobRepository.findById(id);
        if (jobOptional.isPresent()) {
            Job job = jobOptional.get();
            job.setTitle(updatedJob.getTitle());
            job.setDescription(updatedJob.getDescription());
            job.setMinimumSalary(updatedJob.getMinimumSalary());
            job.setMaximumSalary(updatedJob.getMaximumSalary());
            job.setLocation(updatedJob.getLocation());
            jobRepository.save(job);
            return true;
        }
        return false;
    }

    private JobWithCompanyDTO geJobWithCompanyDTO(Job job) {
        Company company = restTemplate.getForObject(companyServiceUrl + String.format("/%d", job.getCompanyId()),
                Company.class);
        JobWithCompanyDTO dto = new JobWithCompanyDTO();
        dto.setJob(job);
        dto.setCompany(company);
        return dto;
    }
}
