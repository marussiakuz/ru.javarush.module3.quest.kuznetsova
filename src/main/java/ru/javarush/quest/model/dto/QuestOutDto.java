package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class QuestOutDto implements Serializable {

    private Long id;
    private String name;
    private String description;
    private String img;
}
