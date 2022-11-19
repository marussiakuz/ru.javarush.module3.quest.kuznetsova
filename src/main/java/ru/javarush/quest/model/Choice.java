package ru.javarush.quest.model;

import lombok.Data;
import ru.javarush.quest.model.enums.State;

@Data
public class Choice {
    private Long id;
    private String text;
    private State state;
}

