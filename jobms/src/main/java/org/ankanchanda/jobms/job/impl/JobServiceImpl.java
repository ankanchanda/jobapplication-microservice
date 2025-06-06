package org.ankanchanda.jobms.job.impl;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.ankanchanda.jobms.clients.CompanyClient;
import org.ankanchanda.jobms.dto.JobWithCompanyDTO;
import org.ankanchanda.jobms.external.Company;
import org.ankanchanda.jobms.job.Job;
import org.ankanchanda.jobms.job.JobRepository;
import org.ankanchanda.jobms.job.JobService;
import org.ankanchanda.jobms.job.mapper.JobMapper;
import org.springframework.stereotype.Service;

import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import io.github.resilience4j.ratelimiter.annotation.RateLimiter;
import io.github.resilience4j.retry.annotation.Retry;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;
    private CompanyClient companyClient;

    public JobServiceImpl(JobRepository jobRepository, CompanyClient companyClient) {
        this.jobRepository = jobRepository;
        this.companyClient = companyClient;
    }

    @Override
    // name should be same as mentioned in application.ymlfor resilience4j
    // @CircuitBreaker(name = "companyBreaker", fallbackMethod = "companyBreakerFallback")
    // @Retry(name = "companyBreaker", fallbackMethod = "companyBreakerFallback") // 3 attempts
    @RateLimiter(name = "companyBreaker", fallbackMethod = "companyBreakerFallback") // 10 requests per second
    public List<JobWithCompanyDTO> findAll() {
        List<Job> jobs = jobRepository.findAll();
        List<JobWithCompanyDTO> jobWithCompanyDTOs = jobs.stream()
                .map(this::geJobWithCompanyDTO)
                .collect(Collectors.toList());
        return jobWithCompanyDTOs;
    }

    public List<String> companyBreakerFallback(Exception e) {
        return List.of("Company service is down, please try again later");
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
        Company company = companyClient.getCompany(job.getCompanyId());
        JobWithCompanyDTO dto = JobMapper.mapToJobWithCompanyDTO(job, company);
        return dto;
    }
}
