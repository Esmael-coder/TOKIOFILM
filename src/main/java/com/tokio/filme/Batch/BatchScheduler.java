package com.tokio.filme.Batch;

import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.parameters.JobParameters;
import org.springframework.batch.core.job.parameters.JobParametersBuilder;
import org.springframework.batch.core.launch.JobOperator;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class BatchScheduler {

    private final Job migrateFilmJob;
    private final JobOperator jobOperator;

    @Scheduled(cron = "0 0 1 * * *")
    public void RunMigrationJob() throws Exception {

        // passando timestamp como parâmetro para o spring saber que cada job é uma instância diferente sendo executada todos os dias.
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("timestamp", System.currentTimeMillis())
                .toJobParameters();

        jobOperator.start(migrateFilmJob, jobParameters);
    }
}
