package ru.javarush.quest.model.quest;

import lombok.Data;

@Data
public class Choice {
    private Long id;
    private String text;
    private State state;
}

