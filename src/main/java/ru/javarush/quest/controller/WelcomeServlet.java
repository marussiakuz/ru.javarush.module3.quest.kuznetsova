package ru.javarush.quest.controller;

import java.io.*;
import javax.servlet.ServletException;
import javax.servlet.http.*;
import javax.servlet.annotation.*;

@WebServlet(name = "welcomeServlet", value = "/welcome")
public class WelcomeServlet extends HttpServlet {

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("application/json;charset=UTF-8");
        System.out.println("!!!!!!!!!!!!!!!!   welcome");
        getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
        //getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("!!!!!!!!!!!!!!!!   POST");
        getServletContext().getRequestDispatcher("/quest.jsp").forward(req, resp);
        //getServletContext().getRequestDispatcher("/welcome.jsp").forward(req, resp);
    }

    public void destroy() {
    }
}