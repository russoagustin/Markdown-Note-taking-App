package com.russo.notes.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Note {
    private Integer id;
    private String name;
    @JsonIgnore
    private String content;

    public Note(String name, String content) {
        this.name = name;
        this.content = content;
    }
    public Note(Integer id,String name, String content) {
        this.id = id;
        this.name = name;
        this.content = content;
    }
    public Note(Integer id,String name) {
        this.name = name;
    }
}
