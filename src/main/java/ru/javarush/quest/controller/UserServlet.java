package ru.javarush.quest.controller;

import ru.javarush.quest.model.user.UserInfo;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;

@WebServlet(name = "userServlet", value = "/user")
public class UserServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        HttpSession currentSession = req.getSession(true);

        UserInfo userInfo = UserInfo.builder()
                .name(req.getParameter("userName"))
                .build();

        currentSession.setAttribute("userInfo", userInfo);

        Cookie userNameCookie = new Cookie("userName", userInfo.getName());
        resp.addCookie(userNameCookie);

        getServletContext().getRequestDispatcher("/quest").forward(req, resp);
    }
}
