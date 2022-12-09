package ru.javarush.quest.repository;

import ru.javarush.quest.model.dto.ChoiceOutDto;
import ru.javarush.quest.model.dto.QuestOutDto;
import ru.javarush.quest.model.dto.StepOutDto;

import java.util.List;
import java.util.Optional;

public interface QuestRepository {

    List<QuestOutDto> getQuests();

    Optional<QuestOutDto> getQuestById(long id);

    Optional<StepOutDto> getStartStepByQuestId(long questId);

    List<ChoiceOutDto> getChoicesByStepId(long stepId);

    Optional<StepOutDto> getStepById(long id);
}
