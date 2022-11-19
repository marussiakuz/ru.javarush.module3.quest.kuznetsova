package ru.javarush.quest.repository;

import ru.javarush.quest.model.dto.ChoiceOutDto;
import ru.javarush.quest.model.dto.QuestOutDto;
import ru.javarush.quest.model.dto.StepOutDto;

import java.util.List;

public interface IQuestRepository {

    List<QuestOutDto> getQuests();

    QuestOutDto getQuestById(long id);

    StepOutDto getStartStepByQuestId(long questId);

    List<ChoiceOutDto> getChoicesByStepId(long stepId);

    StepOutDto getStepById(long id);
}
