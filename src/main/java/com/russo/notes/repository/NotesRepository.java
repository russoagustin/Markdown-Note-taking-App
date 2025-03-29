package com.russo.notes.repository;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;

import com.russo.notes.entity.Note;

@Repository
public class NotesRepository implements INotesRepository {

    private static final String SELECT_BY_ID = "SELECT * FROM notes WHERE id = ?";
    private static final String INSERT = "INSERT INTO notes (name,content) VALUES (?,?)";
    @Autowired
    JdbcTemplate jdbcTemplate;

    RowMapper<Note> rowMapper = ((rs, rowNum)->
        new Note(
        rs.getInt("id"),
        rs.getString("name"),
        rs.getString("content")
    ));

    @Override
    public Optional<Note> findById(Integer id) {
        return Optional.ofNullable(jdbcTemplate.queryForObject(SELECT_BY_ID, rowMapper,id));
    }

    @Override
    public Note save(Note note) {
        jdbcTemplate.update(INSERT,note.getName(),note.getContent());
        return jdbcTemplate.queryForObject("Select * from notes where name like ?", rowMapper, note.getName());
    }
    

}
