package ru.javarush.quest.controller;

import lombok.extern.slf4j.Slf4j;

import ru.javarush.quest.model.dto.UserShortDto;
import ru.javarush.quest.service.UserService;

import javax.inject.Inject;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@Slf4j
@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    @Inject
    private UserService userService;

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        HttpSession currentSession = req.getSession(true);

        UserShortDto userShortDto = userService.addUser(req.getParameter("userName"), req.getRemoteAddr());

        currentSession.setAttribute("userInfo", userShortDto);
        currentSession.setAttribute("isStart", true);
        currentSession.setAttribute("winCount", 0);
        currentSession.setAttribute("failureCount", 0);

        Cookie userNameCookie = new Cookie("userName", userShortDto.getName());
        resp.addCookie(userNameCookie);

        resp.sendRedirect("/quest");
    }
}
