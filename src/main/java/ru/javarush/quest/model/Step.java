package ru.javarush.quest.model;

import lombok.Data;

import java.util.List;

@Data
public class Step {
    private Long id;
    private String img;
    private String text;
    private List<Choice> choices;
}
