package ru.javarush.quest.controller;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
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

import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;

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
    @InjectMocks
    private QuestServlet questServlet;
    private StepOutDto step;
    private ChoiceOutDto choiceWin;
    private ChoiceOutDto choiceFailure;
    private ChoiceOutDto choiceHasNextStep;

    @BeforeEach
    void setUp() throws ServletException, IOException {
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

        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(req.getSession())
                .thenReturn(session);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        doNothing()
                .when(requestDispatcher).forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenForwardToWinPage() throws ServletException, IOException {
        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn("11");

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(step);

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/win.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }
}