package ru.javarush.quest.model.user;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum Status {
    IN_PROCESS("IN_PROCESS"),
    WINNING("WINNING"),
    FAILED("FAILED");

    private final String status;
}
