package ru.javarush.quest.service.impl;

import ru.javarush.quest.model.dto.UserShortDto;
import ru.javarush.quest.service.UserService;

import javax.annotation.ManagedBean;

@ManagedBean
public class UserServiceImpl implements UserService {

    @Override
    public UserShortDto addUser(String name, String ip) {

        return UserShortDto.builder()
                .name(name)
                .ip(ip)
                .build();
    }
}
