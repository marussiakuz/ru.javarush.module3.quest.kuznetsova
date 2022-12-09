package ru.javarush.quest.exception;

public class QuestNotFoundException extends EntityNotFoundException {

    public QuestNotFoundException(String message) {
        super(message);
    }
}
