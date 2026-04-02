package com.engineer.batchprocessing.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.*;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.repository.JobExecutionAlreadyRunningException;
import org.springframework.batch.core.repository.JobInstanceAlreadyCompleteException;
import org.springframework.batch.core.repository.JobRestartException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/job")
public class JobController {

    private final JobLauncher jobLauncher;
    private final Job job;

    public
    JobController (JobLauncher jobLauncher, Job job) {
        this.jobLauncher = jobLauncher;
        this.job = job;
    }


    @PostMapping("/run")
    public ResponseEntity<String> importCsvToDBJob() {
        try {
            JobParameters jobParameter = new JobParametersBuilder (  )
                    .addLong ("startAt", System.currentTimeMillis ())
                    .toJobParameters ();
            JobExecution jobExecution = jobLauncher.run (job, jobParameter);
            return ResponseEntity.ok(
                    "Job Status : " + jobExecution.getStatus()
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Job failed : " + e.getMessage());
        }
    }

    @PostMapping("/health")
    public ResponseEntity<String> healthCheck() {
        try {
            return ResponseEntity.ok(
                    "Health is ok"
            );
        } catch (Exception e) {
            return ResponseEntity.internalServerError()
                    .body("Health is down");
        }
    }
}
