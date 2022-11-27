package ru.javarush.quest.service;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javarush.quest.exception.QuestNotFoundException;
import ru.javarush.quest.exception.StepNotFoundException;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;
import ru.javarush.quest.repository.QuestRepository;
import ru.javarush.quest.service.impl.QuestServiceImpl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class QuestServiceImplTest {

    @Mock
    private QuestRepository questRepository;

    @InjectMocks
    private QuestServiceImpl questServiceImpl;

    private QuestOutDto quest;
    private StepOutDto step;
    private ChoiceOutDto choiceWin;
    private ChoiceOutDto choiceHasNextStep;

    @BeforeEach
    void setUp() {
        quest = QuestOutDto.builder()
                .id(11L)
                .name("quest number one")
                .description("some description")
                .img("/resources/picture.img")
                .build();

        step = StepOutDto.builder()
                .id(18L)
                .question("How are you?")
                .build();

        choiceWin = FinishedChoiceOutDto.builder()
                .id(11L)
                .answer("I'm a cat")
                .state(State.WIN)
                .text("Congratulations, you are cat!")
                .build();

        choiceHasNextStep = ContinueChoiceOutDto.builder()
                .id(1L)
                .nextStepId(22L)
                .answer("I dont know")
                .state(State.CONTINUE)
                .build();
    }

    @Test
    void whenGetQuestsThenCallGetQuestsRepository() {
        Mockito
                .when(questRepository.getQuests())
                .thenReturn(List.of(quest));

        List<QuestOutDto> quests = questServiceImpl.getQuests();

        Assertions.assertNotNull(quests);
        assertEquals(1, quests.size());
        Assertions.assertEquals(quest, quests.get(0));

        Mockito.verify(questRepository, Mockito.times(1))
                .getQuests();
    }

    @Test
    void whenGetQuestsIfNoQuestThenReturnEmptyList() {
        Mockito
                .when(questRepository.getQuests())
                .thenReturn(new ArrayList<>());

        List<QuestOutDto> quests = questServiceImpl.getQuests();

        Assertions.assertNotNull(quests);
        assertTrue(quests.isEmpty());

        Mockito.verify(questRepository, Mockito.times(1))
                .getQuests();
    }

    @Test
    void whenGetStartStepByQuestIdThenCallGetStartStepAndGetChoicesByStepIdRepository() {
        Mockito
                .when(questRepository.getStartStepByQuestId(5))
                .thenReturn(Optional.of(step));

        Mockito
                .when(questRepository.getChoicesByStepId(18))
                .thenReturn(List.of(choiceWin, choiceHasNextStep));

        StepOutDto returned = questServiceImpl.getStartStepByQuestId(5);

        Assertions.assertNotNull(returned);
        assertEquals(List.of(choiceWin, choiceHasNextStep), returned.getChoices());
        assertEquals(18, returned.getId());
        assertEquals("How are you?", returned.getQuestion());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStartStepByQuestId(5);
        Mockito.verify(questRepository, Mockito.times(1))
                .getChoicesByStepId(18);
    }

    @Test
    void whenGetStartStepByQuestIdIfStartStepHasNoChoicesThenThrowsStepNotFoundException() {
        Mockito
                .when(questRepository.getStartStepByQuestId(5))
                .thenReturn(Optional.of(step));

        Mockito
                .when(questRepository.getChoicesByStepId(18))
                .thenReturn(new ArrayList<>());

        final StepNotFoundException exception = Assertions.assertThrows(
                StepNotFoundException.class,
                () -> questServiceImpl.getStartStepByQuestId(5));

        Assertions.assertEquals("start step of quest with id=5 has no any choices", exception.getMessage());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStartStepByQuestId(5);
        Mockito.verify(questRepository, Mockito.times(1))
                .getChoicesByStepId(18);
    }

    @Test
    void whenGetStartStepByQuestIdIfNotFoundStartStepThenThrowsStepNotFoundException() {
        Mockito
                .when(questRepository.getStartStepByQuestId(5))
                .thenReturn(Optional.empty());

        final StepNotFoundException exception = Assertions.assertThrows(
                StepNotFoundException.class,
                () -> questServiceImpl.getStartStepByQuestId(5));

        Assertions.assertEquals("start step of quest with id=5 not found", exception.getMessage());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStartStepByQuestId(5);
        Mockito.verify(questRepository, Mockito.never())
                .getChoicesByStepId(18);
    }

    @Test
    void whenGetQuestByIdThenCallGetQuestByIdRepository() {
        Mockito
                .when(questRepository.getQuestById(11))
                .thenReturn(Optional.of(quest));

        QuestOutDto returnedQuest = questServiceImpl.getQuestById(11);

        Assertions.assertNotNull(returnedQuest);
        assertEquals(quest, returnedQuest);

        Mockito.verify(questRepository, Mockito.times(1))
                .getQuestById(11);
    }

    @Test
    void whenGetQuestByIdIfQuestNotFoundThenThrows() {
        Mockito
                .when(questRepository.getQuestById(3))
                .thenReturn(Optional.empty());

        QuestNotFoundException exception = Assertions.assertThrows(
                QuestNotFoundException.class,
                () -> questServiceImpl.getQuestById(3));

        Assertions.assertEquals("quest with id=3 not found", exception.getMessage());

        Mockito.verify(questRepository, Mockito.times(1))
                .getQuestById(3);
    }

    @Test
    void whenGetStepByIdThenCallGetStepByIdAndGetChoicesByStepIdRepository() {
        Mockito
                .when(questRepository.getStepById(18))
                .thenReturn(Optional.of(step));

        Mockito
                .when(questRepository.getChoicesByStepId(18))
                .thenReturn(List.of(choiceWin, choiceHasNextStep));

        StepOutDto returned = questServiceImpl.getStepById(18);

        Assertions.assertNotNull(returned);
        assertEquals(List.of(choiceWin, choiceHasNextStep), returned.getChoices());
        assertEquals(18, returned.getId());
        assertEquals("How are you?", returned.getQuestion());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStepById(18);
        Mockito.verify(questRepository, Mockito.times(1))
                .getChoicesByStepId(18);
    }

    @Test
    void whenGetStepByIdIfStepHasNoChoicesThenThrowsStepNotFoundException() {
        Mockito
                .when(questRepository.getStepById(18))
                .thenReturn(Optional.of(step));

        Mockito
                .when(questRepository.getChoicesByStepId(18))
                .thenReturn(new ArrayList<>());

        final StepNotFoundException exception = Assertions.assertThrows(
                StepNotFoundException.class,
                () -> questServiceImpl.getStepById(18));

        Assertions.assertEquals("step with id=18 has no any choices", exception.getMessage());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStepById(18);
        Mockito.verify(questRepository, Mockito.times(1))
                .getChoicesByStepId(18);
    }

    @Test
    void whenGetStepByIdIfNotFoundStepThenThrowsStepNotFoundException() {
        Mockito
                .when(questRepository.getStepById(18))
                .thenReturn(Optional.empty());

        final StepNotFoundException exception = Assertions.assertThrows(
                StepNotFoundException.class,
                () -> questServiceImpl.getStepById(18));

        Assertions.assertEquals("step with id=18 not found", exception.getMessage());

        Mockito.verify(questRepository, Mockito.times(1))
                .getStepById(18);
        Mockito.verify(questRepository, Mockito.never())
                .getChoicesByStepId(18);
    }
}