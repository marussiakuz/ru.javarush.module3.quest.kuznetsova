package ru.javarush.quest.controller;

import ru.javarush.quest.model.quest.QuestOutDto;
import ru.javarush.quest.model.quest.Step;
import ru.javarush.quest.model.quest.StepOutDto;
import ru.javarush.quest.service.IQuestService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.List;

@WebServlet(name = "questServlet", value = "/quest/*")
public class QuestServlet extends HttpServlet {

    @Inject
    private IQuestService questService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        if (req.getPathInfo() != null) makeStep(req, resp);

        List<QuestOutDto> quests = questService.getQuests();

        req.setAttribute("quests", quests);

        getServletContext().getRequestDispatcher("/quest.jsp").forward(req, resp);
    }

    private void makeStep(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        HttpSession currentSession = req.getSession();

        if(currentSession.getAttribute("questId") == null) startQuest(req);

        getServletContext().getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    public void init() {
        try {
            Class.forName("org.h2.Driver");
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
    }

    private void startQuest(HttpServletRequest req) {
        int questId = Integer.parseInt(req.getPathInfo().split("/")[1]);

        HttpSession currentSession = req.getSession();

        StepOutDto startStep = questService.getStartStepByQuestId(questId);

        currentSession.setAttribute("questId", questId);
        currentSession.setAttribute("currentStep", startStep);
    }
}
