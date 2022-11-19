package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

@Builder
@Data
public class UserShortDto {

    private String name;
    private String ip;
}
