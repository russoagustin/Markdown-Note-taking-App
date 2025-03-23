package com.russo.notes.entity;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@NoArgsConstructor
@Getter @Setter
public class Note {
    private Integer id;
    private String text;

    public Note(String text) {
        this.text = text;
    }
    public Note(Integer id,String text) {
        this.text = text;
    }
}
