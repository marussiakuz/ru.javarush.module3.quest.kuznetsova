package ru.javarush.quest.service;

import ru.javarush.quest.model.dto.UserShortDto;

import javax.annotation.ManagedBean;

@ManagedBean
public class UserService implements IUserService {

    @Override
    public UserShortDto addUser(String name, String ip) {

        return UserShortDto.builder()
                .name(name)
                .ip(ip)
                .build();
    }
}
