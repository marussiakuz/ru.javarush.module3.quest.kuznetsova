package ru.javarush.quest.model.quest;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum State {
    FAILURE("FAILURE"),
    WIN("WIN"),
    CONTINUE("CONTINUE");

    private final String state;
}
