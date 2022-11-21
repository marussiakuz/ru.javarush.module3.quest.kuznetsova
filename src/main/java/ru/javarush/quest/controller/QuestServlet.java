package ru.javarush.quest.controller;

import lombok.extern.slf4j.Slf4j;
import ru.javarush.quest.exception.ChoiceContentException;
import ru.javarush.quest.exception.ChoiceNotFoundException;
import ru.javarush.quest.exception.SessionInvalidException;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.service.IQuestService;

import javax.inject.Inject;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
import java.io.IOException;
import java.util.Objects;

@Slf4j
@WebServlet(name = "questServlet", value = "/quest/*")
public class QuestServlet extends HttpServlet {

    @Inject
    private IQuestService questService;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        if (req.getParameter("choice") != null) makeStep(req, resp);

        else if (req.getPathInfo() != null) startQuest(req, resp);

        else {
            req.setAttribute("quests", questService.getQuests());
            getServletContext().getRequestDispatcher("/quest.jsp").forward(req, resp);
        }
    }

    private void startQuest(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getSession().setAttribute("isStart", false);

        long questId = Long.parseLong(req.getPathInfo().split("/")[1]);

        HttpSession currentSession = req.getSession();

        StepOutDto startStep = questService.getStartStepByQuestId(questId);
        QuestOutDto quest = questService.getQuestById(questId);

        currentSession.setAttribute("currentStep", startStep);
        currentSession.setAttribute("countOfStep", 1);
        currentSession.setAttribute("currentQuest", quest);

        req.getRequestDispatcher("/game.jsp").forward(req, resp);
    }

    private void makeStep(HttpServletRequest req, HttpServletResponse resp)
            throws ServletException, IOException {
        HttpSession currentSession = req.getSession();
        ChoiceOutDto choice = getChoice(req);

        switch (choice.getState()) {
            case WIN:
                updateSessionAfterFinish(currentSession, ((FinishedChoiceOutDto) choice).getText(), true);
                getServletContext().getRequestDispatcher("/win.jsp").forward(req, resp);
                break;
            case FAILURE:
                req.setAttribute("questId", extractQuest(currentSession).getId());
                updateSessionAfterFinish(currentSession, ((FinishedChoiceOutDto) choice).getText(), false);
                getServletContext().getRequestDispatcher("/failure.jsp").forward(req, resp);
                break;
            case CONTINUE:
                ContinueChoiceOutDto continueChoiceOutDto = (ContinueChoiceOutDto) choice;
                StepOutDto nextStep = questService.getStepById(continueChoiceOutDto.getNextStepId());
                currentSession.setAttribute("currentStep", nextStep);
                incrementAttributeIntValue(currentSession, "countOfStep");
                getServletContext().getRequestDispatcher("/game.jsp").forward(req, resp);
                break;
            default:
                throw new ChoiceContentException(String.format("The choice with id=%s has unknown state %s ",
                        choice.getId(), choice.getState()));
        }
    }

    private ChoiceOutDto getChoice(HttpServletRequest req) {
        long choiceId = Long.parseLong(req.getParameter("choice"));

        HttpSession currentSession = req.getSession();

        StepOutDto currentStep = extractCurrentStep(currentSession);

        return currentStep.getChoices().stream()
                .filter(choiceOutDto -> Objects.equals(choiceOutDto.getId(), choiceId))
                .findFirst()
                .orElseThrow(() -> new ChoiceNotFoundException(String.format("The choice with id=%s didn't found",
                        choiceId)));
    }

    private StepOutDto extractCurrentStep(HttpSession currentSession) {
        Object fieldAttribute = currentSession.getAttribute("currentStep");

        if (StepOutDto.class != fieldAttribute.getClass()) {
            currentSession.invalidate();
            throw new SessionInvalidException("Session is broken, try one more time");
        }

        return (StepOutDto) fieldAttribute;
    }

    private QuestOutDto extractQuest(HttpSession currentSession) {
        Object fieldAttribute = currentSession.getAttribute("currentQuest");

        if (QuestOutDto.class != fieldAttribute.getClass()) {
            currentSession.invalidate();
            throw new SessionInvalidException("Session is broken, try one more time");
        }

        return (QuestOutDto) fieldAttribute;
    }

    private void incrementAttributeIntValue(HttpSession currentSession, String name) {
        Integer count = currentSession.getAttribute(name) != null ? (Integer) currentSession.getAttribute(name) + 1 : 1;
        currentSession.setAttribute(name, count);
    }

    private void updateSessionAfterFinish(HttpSession currentSession, String text, boolean isWin) {
        currentSession.setAttribute("countOfStep", 0);
        currentSession.setAttribute("text", text);

        currentSession.removeAttribute("currentStep");
        currentSession.removeAttribute("currentQuest");

        incrementAttributeIntValue(currentSession, isWin? "winCount" : "failureCount");
    }
}
