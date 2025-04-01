package org.ankanchanda.jobms.dto;

import org.ankanchanda.jobms.external.Company;
import org.ankanchanda.jobms.job.Job;

public class JobWithCompanyDTO {

    private Job job;
    private Company company;

    public Job getJob() {
        return job;
    }
    public void setJob(Job job) {
        this.job = job;
    }
    public Company getCompany() {
        return company;
    }
    public void setCompany(Company company) {
        this.company = company;
    }

}
