package org.ankanchanda.jobms.job.mapper;

import org.ankanchanda.jobms.dto.JobWithCompanyDTO;
import org.ankanchanda.jobms.external.Company;
import org.ankanchanda.jobms.job.Job;

public class JobMapper {
    public static JobWithCompanyDTO mapToJobWithCompanyDTO (Job job, Company company) {
        JobWithCompanyDTO jobWithCompanyDTO = new JobWithCompanyDTO();
        jobWithCompanyDTO.setId(job.getId());
        jobWithCompanyDTO.setTitle(job.getTitle());
        jobWithCompanyDTO.setDescription(job.getDescription());
        jobWithCompanyDTO.setLocation(job.getLocation());
        jobWithCompanyDTO.setMaximumSalary(job.getMaximumSalary());
        jobWithCompanyDTO.setMinimumSalary(job.getMinimumSalary());
        jobWithCompanyDTO.setCompany(company);

        return jobWithCompanyDTO;
    }
}
