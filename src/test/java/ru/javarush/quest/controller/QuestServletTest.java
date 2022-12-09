package ru.javarush.quest.controller;

import org.junit.jupiter.api.*;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.*;
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
import ru.javarush.quest.model.Quest;
import ru.javarush.quest.model.Step;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;
import ru.javarush.quest.service.QuestService;

import java.io.IOException;
import java.util.List;

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

    @BeforeEach
    void setUp() {
        step = StepOutDto.builder()
                .id(18L)
                .question("Who are you?")
                .build();

        quest = QuestOutDto.builder()
                .id(13L)
                .name("Current quest")
                .description("very difficult")
                .img("/resources/current.ipg")
                .build();

        ChoiceOutDto choiceWin = FinishedChoiceOutDto.builder()
                .id(11L)
                .answer("I'm a cat")
                .state(State.WIN)
                .text("Congratulations, you are cat!")
                .build();

        ChoiceOutDto choiceFailure = FinishedChoiceOutDto.builder()
                .id(12L)
                .answer("I'm a cookie")
                .state(State.FAILURE)
                .text("Sad news, you've been eaten..")
                .build();

        ChoiceOutDto choiceHasNextStep = ContinueChoiceOutDto.builder()
                .id(13L)
                .nextStepId(22L)
                .answer("I dont know")
                .state(State.CONTINUE)
                .build();

        step.setChoices(List.of(choiceWin, choiceFailure, choiceHasNextStep));
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenForwardToWinPage() throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/win.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 100000, 999999999})
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenIncrementWinCount(int count)
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        Mockito
                .when(session.getAttribute("winCount"))
                .thenReturn(count);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("winCount", ++count);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenResetAttrCountOfStep() throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 0);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenRemoveAttributesCurrentStepAndQuest()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentStep");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentQuest");
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenSetAttributeText() throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("text", "Congratulations, you are cat!");
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsWinThenDoesNotInvalidateSession()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.never()).invalidate();
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenForwardToFailurePage() throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/failure.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenResetCountOfStep() throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 0);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenSetAttributeText() throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("text", "Sad news, you've been eaten..");
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenRemoveAttributesCurrentStepAndQuest()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentStep");
        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentQuest");
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 100000, 999999999})
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenIncrementFailureCount(int failureCount)
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);
        Mockito
                .when(session.getAttribute("failureCount"))
                .thenReturn(failureCount);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("failureCount", ++failureCount);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenSetAttributeQuestIdOfCurrentQuest()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .getAttribute("currentQuest");
        Mockito.verify(req, Mockito.times(1))
                .setAttribute("questId", quest.getId());
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceIsFailureThenDoesNotInvalidateSession()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.never()).invalidate();
    }

    @Test
    void whenDoGetIfCurrentQuestAttributeIsNotQuestOutDtoThenThrowsSessionInvalidException()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(Quest.class);

        SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class,
                () -> questServlet.doGet(req, resp));

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher("/failure.jsp");
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(2))
                .getAttribute(Mockito.anyString());
        Mockito.verify(session, Mockito.times(1))
                .invalidate();
    }

    @Test
    void whenDoGetIfWinCountNullThenSetOne() throws ServletException, IOException {
        setParamChoiceAndAttrStep("11", step);

        Mockito
                .when(session.getAttribute("winCount"))
                .thenReturn(null);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .removeAttribute("currentStep");
    }

    @Test
    void whenDoGetIfFailureCountNullThenSetOne() throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(quest);
        Mockito
                .when(session.getAttribute("failureCount"))
                .thenReturn(null);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("failureCount", 1);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceHasNextStepThenForwardToGamePage() throws ServletException, IOException {
        setParamChoiceAndAttrStep("13", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/game.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceHasNextStepThenSetAttributeCurrentStep()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("13", step);

        StepOutDto nextStep = StepOutDto.builder().build();

        Mockito
                .when(questService.getStepById(22))
                .thenReturn(nextStep);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(questService, Mockito.times(1))
                .getStepById(Mockito.anyLong());
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentStep", nextStep);
    }

    @ParameterizedTest
    @ValueSource(ints = {0, 15, 100000, 999999999})
    void whenDoGetIfRequestHasChoiceParamAndChoiceHasNextStepThenIncrementAttributeCountOfStep(int countOfStep)
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("13", step);

        Mockito
                .when(session.getAttribute("countOfStep"))
                .thenReturn(countOfStep);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", ++countOfStep);
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceHasNextStepThenDoesNotInvalidateSession()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("13", step);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.never()).invalidate();
    }

    @Test
    void whenDoGetIfCountOfStepNullThenSetOne() throws ServletException, IOException {
        setParamChoiceAndAttrStep("13", step);

        Mockito
                .when(session.getAttribute("countOfStep"))
                .thenReturn(null);

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 1);
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "12", "13"})
    void whenDoGetIfCurrentStepIsNotStepOutDtoThenThrowsSessionInvalidException(String choiceId)
            throws ServletException, IOException {
        setParamChoiceAndAttrStep(choiceId, Step.class);

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .invalidate();
    }

    @ParameterizedTest
    @ValueSource(strings = {"11", "12", "13"})
    void whenDoGetIfCurrentStepIsNullThenThrowsSessionInvalidException(String choiceId)
            throws ServletException, IOException {
        setParamChoiceAndAttrStep(choiceId, null);

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .invalidate();
    }

    @Test
    void whenDoGetIfCurrentQuestIsNotStepOutDtoThenThrowsSessionInvalidException()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(Quest.class);

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .invalidate();
    }

    @Test
    void whenDoGetIfCurrentQuestIsNullThenThrowsSessionInvalidException()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("12", step);

        Mockito
                .when(session.getAttribute("currentQuest"))
                .thenReturn(null);

        final SessionInvalidException exception = Assertions.assertThrows(
                SessionInvalidException.class, () -> questServlet.doGet(req, resp)
        );

        Assertions.assertEquals("Session is broken, try one more time", exception.getMessage());

        Mockito.verify(servletContext, Mockito.never())
                .getRequestDispatcher(Mockito.anyString());
        Mockito.verify(requestDispatcher, Mockito.never())
                .forward(req, resp);
        Mockito.verify(session, Mockito.times(1))
                .invalidate();
    }

    @Test
    void whenDoGetIfRequestHasChoiceParamAndChoiceNotExistsThenThrowsChoiceNotFoundException()
            throws ServletException, IOException {
        setParamChoiceAndAttrStep("111", step);

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
        setPathInfo();

        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(req, Mockito.times(1))
                .getRequestDispatcher("/game.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfRequestHasNotChoiceParamAndHasPathInfoThenSetAttributeIsStartFalse()
            throws ServletException, IOException {
        setPathInfo();

        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("isStart", false);
    }

    @Test
    void whenDoGetIfRequestHasNotChoiceParamAndHasPathInfoThenSetStartStepAsAttributeCurrentStep()
            throws ServletException, IOException {
        setPathInfo();

        Mockito
                .when(questService.getStartStepByQuestId(13))
                .thenReturn(step);
        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(questService, Mockito.times(1))
                .getStartStepByQuestId(Mockito.anyLong());
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentStep", step);
    }

    @Test
    void whenDoGetIfRequestHasNotChoiceParamAndHasPathInfoThenSetAttributeCountOfStepOne()
            throws ServletException, IOException {
        setPathInfo();

        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(session, Mockito.times(1))
                .setAttribute("countOfStep", 1);
    }

    @Test
    void whenDoGetIfRequestHasNotChoiceParamAndHasPathInfoThenSetAttributeCurrentQuest()
            throws ServletException, IOException {
        setPathInfo();

        Mockito
                .when(questService.getQuestById(13))
                .thenReturn(quest);
        Mockito
                .when(req.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        questServlet.doGet(req, resp);

        Mockito.verify(questService, Mockito.times(1))
                .getQuestById(Mockito.anyLong());
        Mockito.verify(session, Mockito.times(1))
                .setAttribute("currentQuest", quest);
    }

    @Test
    void whenDoGetIfChoiceParamAndPathInfoAreNullThenForwardToQuestPage() throws ServletException, IOException {
        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/quest.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }

    @Test
    void whenDoGetIfChoiceParamAndPathInfoAreNullThenSetAttributeQuests() throws ServletException, IOException {
        Mockito
                .when(questService.getQuests())
                .thenReturn(List.of(quest));

        forwardWithMockito();

        questServlet.doGet(req, resp);

        Mockito.verify(questService, Mockito.times(1))
                .getQuests();
        Mockito.verify(req, Mockito.times(1))
                .setAttribute("quests", List.of(quest));
    }

    private void setParamChoiceAndAttrStep(String choice, Object currentStep) {
        Mockito
                .when(req.getSession())
                .thenReturn(session);

        Mockito
                .when(req.getParameter("choice"))
                .thenReturn(choice);

        Mockito
                .when(session.getAttribute("currentStep"))
                .thenReturn(currentStep);
    }

    private void forwardWithMockito() {
        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);
    }

    private void setPathInfo() {
        Mockito
                .when(req.getSession())
                .thenReturn(session);
        Mockito
                .when(req.getPathInfo())
                .thenReturn("quest/13");
    }
}