package ru.javarush.quest.repository;

import ru.javarush.quest.model.quest.*;

import java.util.List;
import java.util.Optional;

public interface IQuestRepository {

    List<QuestOutDto> getQuests();

    Optional<Quest> getQuestById();

    StepOutDto getStartStepByQuestId(long questId);

    List<ChoiceOutDto> getChoicesByStepId(long stepId);
}
