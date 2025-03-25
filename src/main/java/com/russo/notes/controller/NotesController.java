package com.russo.notes.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.russo.notes.entity.Note;
import com.russo.notes.repository.INotesRepository;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController()
@RequestMapping("api/notes")
public class NotesController {

    @Autowired
    private INotesRepository notesRepository;
    
    @PostMapping()
    public ResponseEntity<?> postMethodName(@RequestParam("file") MultipartFile file) {
        byte[] content;
        String name;
        System.out.println(file.getContentType());
        if (file.isEmpty() || file.getContentType() != "text/markdown") {
            return ResponseEntity.badRequest().build();
        }
        try {
            content = file.getBytes();
            name = file.getOriginalFilename();
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        notesRepository.save(new Note(name,content));
        return ResponseEntity.created(null).build();
    }
    

}
