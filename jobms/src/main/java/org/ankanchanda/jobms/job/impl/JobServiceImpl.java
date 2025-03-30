package org.ankanchanda.jobms.job.impl;

import java.util.List;
import java.util.Optional;

import org.ankanchanda.jobms.job.Job;
import org.ankanchanda.jobms.job.JobRepository;
import org.ankanchanda.jobms.job.JobService;
import org.springframework.stereotype.Service;

@Service
public class JobServiceImpl implements JobService {
    private JobRepository jobRepository;

    public JobServiceImpl(JobRepository jobRepository) {
        this.jobRepository = jobRepository;
    }

    @Override
    public List<Job> findAll() {
        return jobRepository.findAll();
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
        if(jobOptional.isPresent()){
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
}
