package ru.javarush.quest.exception;

public class DataSourceIsNotAvailableException extends RuntimeException {

    public DataSourceIsNotAvailableException(String message) {
        super(message);
    }
}
