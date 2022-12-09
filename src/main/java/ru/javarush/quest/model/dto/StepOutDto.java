package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class StepOutDto implements Serializable {

    private Long id;
    private String question;
    private List<ChoiceOutDto> choices;
}
