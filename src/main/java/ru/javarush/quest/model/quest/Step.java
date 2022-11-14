package ru.javarush.quest.model.quest;

import lombok.Data;
import ru.javarush.quest.model.quest.Choice;

import java.util.List;

@Data
public class Step {
    private Long id;
    private String img;
    private String text;
    private List<Choice> choices;
}
