package ru.javarush.quest.service;

import ru.javarush.quest.model.dto.UserShortDto;

public interface IUserService {

    UserShortDto addUser(String name, String ip);
}
