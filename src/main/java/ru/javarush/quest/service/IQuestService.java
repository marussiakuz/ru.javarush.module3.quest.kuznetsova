package ru.javarush.quest.service;

import ru.javarush.quest.model.quest.QuestOutDto;
import ru.javarush.quest.model.quest.Step;
import ru.javarush.quest.model.quest.StepOutDto;

import java.util.List;

public interface IQuestService {

    List<QuestOutDto> getQuests();

    StepOutDto getStartStepByQuestId(long questId);
}
