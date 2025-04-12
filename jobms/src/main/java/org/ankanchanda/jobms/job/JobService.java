package org.ankanchanda.jobms.job;

import java.util.List;
import org.ankanchanda.jobms.dto.JobWithCompanyDTO;

public interface JobService {
    List<JobWithCompanyDTO> findAll();

    JobWithCompanyDTO findJobById(Long id);

    void createJob(Job job);

    boolean deleteJob(Long id);

    boolean updateJob(Long id, Job job);
}