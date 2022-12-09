package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserShortDto implements Serializable {

    private Long id;
    private String name;
    private String ip;
}
