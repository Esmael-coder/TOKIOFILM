package com.tokio.filme.Batch;


import com.tokio.filme.repositories.FilmRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.job.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class MigrateJobListener implements JobExecutionListener {

    private final FilmRepository filmRepository;

    @Override
    public void beforeJob(JobExecution jobExecution){
        long count = filmRepository.countByMigratedFalse();

        log.info("Filmes disponíveis para migrar antes do processo: {}", count);
        log.info("Iniciando processo {}", jobExecution.getJobInstance().getJobName());
    }

    @Override
    public void afterJob(JobExecution jobExecution){
        long count = filmRepository.countByMigratedFalse();

        log.info("Fim do processo {}", jobExecution.getJobInstance().getJobName());
        log.info("Filmes disponíveis para migrar depois do processo:  {}", count);
    }
}
