package com.russo.notes.repository;

import java.util.Optional;

import com.russo.notes.entity.Note;

public interface INotesRepository {
    Note save(Note note);
    Optional<Note> findById(Integer id);
}
