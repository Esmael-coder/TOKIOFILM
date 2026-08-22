package com.tokio.filme.Batch;

import com.tokio.filme.dtos.FilmMigrationDTO;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.listener.ItemWriteListener;
import org.springframework.batch.infrastructure.item.Chunk;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.time.LocalDate;

@Slf4j
@Component
@RequiredArgsConstructor
public class FilmWriterListener implements ItemWriteListener<FilmMigrationDTO> {

    private final JdbcTemplate jdbcTemplate;

    @Override
    public void afterWrite(Chunk<? extends FilmMigrationDTO> films) {

        for (FilmMigrationDTO film : films) {

            jdbcTemplate.update("""
                    UPDATE film
                    SET migrated = true,
                        date_migrate = CURRENT_DATE
                    WHERE id = ?
                    """,
                    film.id()
            );

            log.info("Filme {} migrado em {}", film.id(), LocalDate.now());
        }
    }
}
