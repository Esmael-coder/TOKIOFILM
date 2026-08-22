package com.tokio.filme.Batch;

import com.tokio.filme.entities.Film;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Component;


import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class FilmRowMapper implements RowMapper<Film> {

    @Override
    public Film mapRow(ResultSet rs, int rowNum) throws SQLException {

        Film film = new Film();

        film.setId(rs.getLong("id"));
        film.setTitle(rs.getString("title"));
        film.setYear(rs.getInt("year"));
        film.setDuration(rs.getString("duration"));
        film.setPoster(rs.getString("poster"));
        film.setSynopsis(rs.getString("synopsis"));

        Date date = rs.getDate("date_migrate");

        if (date != null) {
            film.setDateMigrate(date.toLocalDate());
        }

        return film;
    }
}
