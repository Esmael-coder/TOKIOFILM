package com.tokio.filme.Batch;

import com.tokio.filme.dtos.FilmMigrationDTO;
import com.tokio.filme.entities.Film;
import lombok.RequiredArgsConstructor;
import org.springframework.batch.core.job.Job;
import org.springframework.batch.core.job.builder.JobBuilder;
import org.springframework.batch.core.listener.ItemWriteListener;
import org.springframework.batch.core.repository.JobRepository;
import org.springframework.batch.core.step.Step;
import org.springframework.batch.core.step.builder.StepBuilder;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.batch.infrastructure.item.ItemReader;
import org.springframework.batch.infrastructure.item.ItemWriter;
import org.springframework.batch.infrastructure.item.database.JdbcCursorItemReader;
import org.springframework.batch.infrastructure.item.database.builder.JdbcCursorItemReaderBuilder;
import org.springframework.batch.infrastructure.item.file.FlatFileItemWriter;
import org.springframework.batch.infrastructure.item.file.builder.FlatFileItemWriterBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.FileSystemResource;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
public class BatchConfig {

    private final JobRepository jobRepository;
    private final PlatformTransactionManager transactionManager;

    @Bean
    public Job migrateFilmJob(Step migrateStep, MigrateJobListener migrateJobListener){
        return new JobBuilder("migrateFilmJob", jobRepository)
                .start(migrateStep)
                .listener(migrateJobListener)
                .build();
    }


    @Bean
    public Step migrateStep(ItemReader<Film> filmReader,
                            ItemProcessor<Film, FilmMigrationDTO> filmProcessor,
                            ItemWriter<FilmMigrationDTO> filmWriter,
                            ItemWriteListener filmWriteListener){

        return new StepBuilder("migrateStep", jobRepository)
                .<Film, FilmMigrationDTO> chunk(100)
                .reader(filmReader)
                .processor(filmProcessor)
                .writer(filmWriter)
                .transactionManager(transactionManager)
                .listener(filmWriteListener)
                .build();
    }

    /*
    Configuração para ler no banco de dados
    * */
    @Bean
    public JdbcCursorItemReader<Film> reader(
            DataSource dataSource,
            FilmRowMapper filmRowMapper){

        return new JdbcCursorItemReaderBuilder<Film>()
                .name("filmReader")
                .dataSource(dataSource)
                .sql("""
                        SELECT id,
                        title,
                        year,
                        duration,
                        poster,
                        synopsis,
                        date_migrate
                        FROM film WHERE migrated = false
                        """)
                .rowMapper(filmRowMapper)
                .build();
    }

    /*
    Configuração para exportar para csv file
    * */
    @Bean
    public FlatFileItemWriter<FilmMigrationDTO> csvWriter(){
        return new FlatFileItemWriterBuilder<FilmMigrationDTO>()
                .name("filmCsvWriter")
                .resource(new FileSystemResource("exports/films.csv"))
                .append(true)
                .delimited()
                .delimiter(",")
                .names("id","title","year","duration","synopsis","poster","dateMigrate")
                .headerCallback(writer ->
                        writer.write("id,title,year,duration,synopsis,poster,date_migrate")
                )
                .build();
    }

}
