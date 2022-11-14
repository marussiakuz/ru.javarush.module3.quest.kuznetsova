package ru.javarush.quest.model.quest;

import lombok.Data;

import java.util.List;

@Data
public class Quest {
    private Long id;
    private String img;
    private String name;
    private String description;
    List<Step> steps;
}
