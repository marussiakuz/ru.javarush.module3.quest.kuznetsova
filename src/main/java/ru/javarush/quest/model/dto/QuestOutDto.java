package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class QuestOutDto {

    private Long id;
    private String name;
    private String description;
    private String img;
}
