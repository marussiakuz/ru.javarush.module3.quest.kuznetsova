package ru.javarush.quest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javarush.quest.model.dto.UserShortDto;
import ru.javarush.quest.service.impl.UserServiceImpl;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class UserServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private HttpSession session;
    @Mock
    private UserServiceImpl userServiceImpl;
    @InjectMocks
    private UserServlet userServlet;

    @BeforeEach
    void setUp() throws IOException {
        Mockito
                .when(req.getSession(true))
                .thenReturn(session);

        Mockito
                .when(req.getParameter("userName"))
                .thenReturn("JavaRush");

        Mockito
                .when(req.getRemoteAddr())
                .thenReturn("0.0.0.127.123.55");

        Mockito
                .when(userServiceImpl.addUser(Mockito.anyString(), Mockito.anyString()))
                .thenCallRealMethod();

        userServlet.doPost(req, resp);
    }

    @Test
    void whenDoPostThenCallAddUserWithNameAndIpParam() {
        Mockito
                .verify(userServiceImpl, Mockito.times(1))
                .addUser("JavaRush", "0.0.0.127.123.55");
    }

    @Test
    void whenDoPostThenSetAttributeUserInfoIsStartWinAndFailureCountToSession() {
        Mockito
                .verify(session, Mockito.times(1))
                .setAttribute("userInfo", UserShortDto.builder()
                        .name("JavaRush")
                        .ip("0.0.0.127.123.55")
                        .build());

        Mockito
                .verify(session, Mockito.times(1))
                .setAttribute("isStart", true);

        Mockito
                .verify(session, Mockito.times(1))
                .setAttribute("winCount", 0);

        Mockito
                .verify(session, Mockito.times(1))
                .setAttribute("failureCount", 0);

    }

    @Test
    void whenDoPostThenAddCookieToResponse() {
        Mockito
                .verify(resp, Mockito.times(1))
                .addCookie(Mockito.any(Cookie.class));
    }

    @Test
    void whenDoPostThenSendRedirectToQuest() throws IOException {
        Mockito
                .verify(resp, Mockito.times(1))
                .sendRedirect("/quest");
    }
}