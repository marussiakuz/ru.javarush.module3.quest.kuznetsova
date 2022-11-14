package ru.javarush.quest.service;

import ru.javarush.quest.model.quest.ChoiceOutDto;
import ru.javarush.quest.model.quest.QuestOutDto;
import ru.javarush.quest.model.quest.StepOutDto;
import ru.javarush.quest.repository.IQuestRepository;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import java.util.List;

@ManagedBean
public class QuestService implements IQuestService {

    @Inject
    private IQuestRepository questRepository;

    @Override
    public List<QuestOutDto> getQuests() {
        return questRepository.getQuests();
    }

    @Override
    public StepOutDto getStartStepByQuestId(long questId) {
        StepOutDto startStep = questRepository.getStartStepByQuestId(questId);

        List<ChoiceOutDto> choices = questRepository.getChoicesByStepId(startStep.getId());

        startStep.setChoices(choices);

        return startStep;
    }
}
