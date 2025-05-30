package org.ankanchanda.jobms.job;

import java.util.List;

import org.ankanchanda.jobms.dto.JobWithCompanyDTO;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.PutMapping;

@RestController
@RequestMapping("/jobs")
public class JobController {
    private JobService jobService;

    public JobController(JobService jobService) {
        this.jobService = jobService;
    }

    @GetMapping
    public ResponseEntity<List<JobWithCompanyDTO>> findAll() {
        return ResponseEntity.ok(jobService.findAll());
    }

    @GetMapping("/{id}")
    public ResponseEntity<JobWithCompanyDTO> getJobById(@PathVariable Long id) {
        JobWithCompanyDTO jobWithCompanyDTO = jobService.findJobById(id);
        if (jobWithCompanyDTO == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(jobWithCompanyDTO);
    }

    @PostMapping
    public ResponseEntity<String> createJob(@RequestBody Job job) {
        jobService.createJob(job);
        return new ResponseEntity<>("Job created successfully", HttpStatus.CREATED);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteJob(@PathVariable Long id) {
        if (jobService.deleteJob(id)) {
            return new ResponseEntity<>("Job deleted successfully", HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<String> updateJob(@PathVariable Long id, @RequestBody Job job) {
        if (jobService.updateJob(id, job)) {
            return new ResponseEntity<>("Job updated successfully", HttpStatus.CREATED);
        }
        return ResponseEntity.notFound().build();
    }
}