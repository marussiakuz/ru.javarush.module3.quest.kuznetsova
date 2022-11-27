package ru.javarush.quest.controller;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import ru.javarush.quest.exception.ChoiceNotFoundException;
import ru.javarush.quest.exception.SessionInvalidException;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;
import ru.javarush.quest.service.QuestService;

import java.io.IOException;
import java.util.List;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class QuestServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @Mock
    private HttpSession session;
    @Mock
    private QuestService questService;
    @InjectMocks
    private QuestServlet questServlet;
    private QuestOutDto quest;
    private StepOutDto step;
    private ChoiceOutDto choiceWin;
    private ChoiceOutDto choiceFailure;
    private ChoiceOutDto choiceHasNextStep;

    @BeforeEach
    void setUp() {
        step = StepOutDto.builder()
                .id(18L)
                .question("Who are you?")
                .build();

        choiceWin = FinishedChoiceOutDto.builder()
                .id(11L)
                .answer("I'm a cat")
                .state(State.WIN)
                .text("Congratulations, you are cat!")
                .build();

        choiceFailure = FinishedChoiceOutDto.builder()
                .id(12L)
                .answer("I'm a cookie")
                .state(State.FAILURE)
                .text("Sad news, you've been eaten..")
                .build();

        choiceHasNextStep = ContinueChoiceOutDto.builder()
                .id(13L)
                .nextStepId(22L)
                .answer("I dont know")
                .state(State.CONTINUE)
                .build();

        quest = QuestOutDto.builder()
                .id(13L)
                .name("Current quest")
                .description("very difficult")
                .img("/resources/current.ipg")
                .build();
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenForwardToWinPage() throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn("11");

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(step);

        Mockito
                .when(session.getAttribute("winCount"))
                .thenReturn(15);

        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        doNothing()
                .when(requestDispatcher).forward(req, resp);

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/win.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 0);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("text", "Congratulations, you are cat!");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentStep");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentQuest");
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("winCount", 16);

    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenForwardToFailurePage() throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn("12");

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        Mockito
                .when(session.getAttribute("failureCount"))
                .thenReturn(10);

        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        doNothing()
                .when(requestDispatcher).forward(req, resp);

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/failure.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 0);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("text", "Sad news, you've been eaten..");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentStep");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentQuest");
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("failureCount", 11);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceHasNextStepThenForwardToGamePage() throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));
        StepOutDto nextStep = StepOutDto.builder().build();

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn("13");

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(step);

        Mockito
                .when(questService.getStepById(22))
                .thenReturn(nextStep);

        Mockito
                .when(session.getAttribute("countOfStep"))
                .thenReturn(1);

        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        doNothing()
                .when(requestDispatcher).forward(req, resp);

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/game.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentStep", nextStep);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 2);
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "12", "13"})
    void whenDoGetIfRequestHasChoiceParamAndCurrentStepIsNotStepOutDtoThenThrowsSessionInvalidException(String choiceId)
            throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn(choiceId);

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(new Object());

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "12", "13"})
    void whenDoGetIfRequestHasChoiceParamAndSessionHasNotCurrentStepThenThrowsSessionInvalidException(String choiceId)
            throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn(choiceId);

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(null);

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceNotExistsThenThrows()
            throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn("111");

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(step);

        final ChoiceNotFoundException exception = Assertions.assertThrows(
                ChoiceNotFoundException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("The choice with id=111 didn't found", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasNotChoiceParamAndHasPathInfoThenStartQuestAndForwardToGamePage()
            throws ServletException, IOException {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        Mockito
                .when(req.getPathInfo())
                .thenReturn("quest/13");

        Mockito
                .when(questService.getStartStepByQuestId(13))
                .thenReturn(step);
        Mockito
                .when(questService.getQuestById(13))
                .thenReturn(quest);
        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("isStart", false);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentStep", step);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 1);
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentQuest", quest);
        Mockito.verify(req, Mockito.times(1))
                .getRequestDispatcher("/game.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfChoiceParamAndPathInfoAreNullThenForwardToQuestPage() throws ServletException, IOException {
        Mockito
                .when(questService.getQuests())
                .thenReturn(List.of(quest));
        Mockito
                .when(questServlet.getServletContext())
                .thenReturn(servletContext);
        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(req, Mockito.times(1))
                .setAttribute("quests", List.of(quest));
        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/quest.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }
}