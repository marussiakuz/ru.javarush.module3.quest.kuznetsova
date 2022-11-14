package ru.javarush.quest.model.user;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserInfo {
    private String name;
}
