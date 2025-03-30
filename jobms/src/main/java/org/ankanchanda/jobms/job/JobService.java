package org.ankanchanda.jobms.job;

import java.util.List;

public interface JobService {
    List<Job> findAll();

    Job findJobById(Long id);

    void createJob(Job job);

    boolean deleteJob(Long id);

    boolean updateJob(Long id, Job job);
}