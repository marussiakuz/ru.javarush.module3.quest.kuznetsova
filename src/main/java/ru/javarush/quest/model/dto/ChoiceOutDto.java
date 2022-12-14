package ru.javarush.quest.model.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.experimental.SuperBuilder;
import ru.javarush.quest.model.enums.State;

import java.io.Serializable;

@Data
@SuperBuilder
@AllArgsConstructor
public abstract class ChoiceOutDto implements Serializable {

    private Long id;
    private String answer;
    private State state;
}
