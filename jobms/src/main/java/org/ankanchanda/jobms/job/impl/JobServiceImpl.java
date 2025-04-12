package org.ankanchanda.jobms.job.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ankanchanda.jobms.dto.JobWithCompanyDTO;
import org.ankanchanda.jobms.external.Company;
import org.ankanchanda.jobms.job.Job;
import org.ankanchanda.jobms.job.JobRepository;
import org.ankanchanda.jobms.job.JobService;
import org.ankanchanda.jobms.job.mapper.JobMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    // companyms registered with Eureka
    // This is the URL of the company service. It is used to get the company details for a job.
    private final String companyServiceUrl = "http://COMPANYMS:8081/companies";

    @Autowired
    private RestTemplate restTemplate;


    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
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
    public JobWithCompanyDTO findJobById(Long id) {
        Job job = jobRepository.findById(id).orElse(null);
        return job != null ? geJobWithCompanyDTO(job) : null;
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
        JobWithCompanyDTO dto = JobMapper.mapToJobWithCompanyDTO(job, company);
        return dto;
    }
}
