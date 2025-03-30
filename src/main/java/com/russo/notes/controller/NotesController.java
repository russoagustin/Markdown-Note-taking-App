package com.russo.notes.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.util.UriComponentsBuilder;

import com.russo.notes.entity.Note;
import com.russo.notes.repository.INotesRepository;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Optional;

import org.commonmark.node.Node;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.GetMapping;

@RestController()
@RequestMapping("api/notes")
public class NotesController {

    @Autowired
    private INotesRepository notesRepository;
    
    @PostMapping()
    public ResponseEntity<?> postMethodName(@RequestParam("file") MultipartFile file,UriComponentsBuilder ucb) {
        String content;
        String name;
        System.out.println(file.getContentType());
        if (file.isEmpty() || (!file.getContentType().equals("application/octet-stream") && !file.getContentType().equals("text/markdown"))) {
            System.err.println("tipo no correcto");
            return ResponseEntity.badRequest().build();
        }
        try {
            content = new String(file.getBytes(),StandardCharsets.UTF_8);
            name = file.getOriginalFilename();   
        } catch (IOException e) {
            return ResponseEntity.badRequest().build();
        }

        Note savedNote =notesRepository.save(new Note(name,content));
        System.out.println(savedNote.getId());
        URI uri = ucb.path("api/notes/{id}").buildAndExpand(savedNote.getId()).toUri();
        return ResponseEntity.created(uri).build();
    }
    
    

    @GetMapping("/render")
    public String getMethodName(@RequestParam("id") int id) {
        Optional<Note> optNote = notesRepository.findById(id);
        String content="";
        if (optNote.isPresent()) {
            content = optNote.get().getContent();
        }
        Parser parser = Parser.builder().build();
        Node document = parser.parse(content);
        HtmlRenderer renderer = HtmlRenderer.builder().build();
        
        String html = renderer.render(document);

        //return html;

        String htmlWithStyles = """
        <html>
        <head>
            <link rel="stylesheet" type="text/css" href="/mdstyle.css">
        </head>
        <body>
            %s
        </body>
        </html>
        """.formatted(html);

        return htmlWithStyles;
    }

    @GetMapping()
    public ResponseEntity<List<Note>> listAll(){
        return ResponseEntity.ok(notesRepository.getAll());
    }
    

}
