package ru.javarush.quest.exception;

public class UrlParameterInvalidException extends RuntimeException {

    public UrlParameterInvalidException(String message) {
        super(message);
    }
}
