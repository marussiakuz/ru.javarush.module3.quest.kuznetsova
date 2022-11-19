package ru.javarush.quest.service;

import ru.javarush.quest.model.dto.QuestOutDto;
import ru.javarush.quest.model.dto.StepOutDto;

import java.util.List;

public interface IQuestService {

    List<QuestOutDto> getQuests();

    QuestOutDto getQuestById(long id);

    StepOutDto getStartStepByQuestId(long questId);

    StepOutDto getStepById(long id);
}
