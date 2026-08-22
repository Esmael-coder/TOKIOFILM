package com.tokio.filme.Batch;

import com.tokio.filme.dtos.FilmMigrationDTO;
import com.tokio.filme.entities.Film;
import org.jspecify.annotations.Nullable;
import org.springframework.batch.infrastructure.item.ItemProcessor;
import org.springframework.stereotype.Component;

/*
Na hora de processar, o spring vai procurar automaticamente por essa class
É um ItemProcessor com mesma entrada e saida passada no parâmetro do Step
* */

@Component
public class MigrateFilmProcessor implements ItemProcessor<Film, FilmMigrationDTO> {


    @Override
    public @Nullable FilmMigrationDTO process(Film film) throws Exception {

        FilmMigrationDTO migrationDTO = new FilmMigrationDTO(
                film.getId(),
                film.getTitle(),
                film.getYear(),
                film.getDuration(),
                film.getSynopsis(),
                film.getPoster(),
                film.getDateMigrate()
        );
        return migrationDTO;
    }
}
