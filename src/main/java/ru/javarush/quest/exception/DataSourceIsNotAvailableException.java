package ru.javarush.quest.exception;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class DataSourceIsNotAvailableException extends RuntimeException {

    public DataSourceIsNotAvailableException(String message) {
        super(message);
        log.error(message);
    }
}
