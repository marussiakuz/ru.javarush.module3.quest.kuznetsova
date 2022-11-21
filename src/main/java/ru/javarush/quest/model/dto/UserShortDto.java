package ru.javarush.quest.model.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Builder
@Data
public class UserShortDto implements Serializable {

    private String name;
    private String ip;
}
