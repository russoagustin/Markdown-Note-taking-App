package com.russo.notes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Note {
    private Integer id;
    private String name;
    private byte[] content;

    public Note(String name, byte[] content) {
        this.name = name;
        this.content = content;
    }
    public Note(Integer id,String name) {
        this.name = name;
    }
}
