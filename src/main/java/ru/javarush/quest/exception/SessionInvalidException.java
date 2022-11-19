package ru.javarush.quest.exception;

public class SessionInvalidException extends RuntimeException {

    public SessionInvalidException(String message) {
        super(message);
    }
}
