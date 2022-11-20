package ru.javarush.quest.exception;

public class QuestNotFoundException extends RuntimeException {

    public QuestNotFoundException(String message) {
        super(message);
    }
}
