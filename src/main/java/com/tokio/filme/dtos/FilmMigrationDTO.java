package com.tokio.filme.dtos;

import java.time.LocalDate;

public record FilmMigrationDTO(
        Long id,
        String title,
        Integer year,
        String duration,
        String synopsis,
        String poster,
        LocalDate dateMigrate
) {}
