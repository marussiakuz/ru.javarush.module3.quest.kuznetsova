package ru.javarush.quest.model.quest;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class ChoiceOutDto {

    private Long id;
    private String answer;
    private State state;
}
