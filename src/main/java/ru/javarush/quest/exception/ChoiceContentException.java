package ru.javarush.quest.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class ChoiceContentException extends RuntimeException {

    public ChoiceContentException(String message) {
        super(message);
    }
}
