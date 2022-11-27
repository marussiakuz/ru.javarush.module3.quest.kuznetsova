package ru.javarush.quest.service;

import org.junit.jupiter.api.Test;
import ru.javarush.quest.model.dto.UserShortDto;
import ru.javarush.quest.service.impl.UserServiceImpl;

import static org.junit.jupiter.api.Assertions.*;

class UserServiceImplTest {

    @Test
    void whenAddUserThenReturnUserShortDto() {
        UserShortDto returned = new UserServiceImpl().addUser("name", "0.0.0.127.123.55");

        UserShortDto expected = UserShortDto.builder()
                        .name("name")
                        .ip("0.0.0.127.123.55")
                        .build();

        assertNotNull(returned);
        assertEquals(expected, returned);
        assertEquals("name", returned.getName());
        assertEquals("0.0.0.127.123.55", returned.getIp());
    }
}