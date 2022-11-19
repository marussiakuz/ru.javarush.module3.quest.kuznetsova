package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class StepOutDto {

    private Long id;
    private String question;
    private List<ChoiceOutDto> choices;
}
