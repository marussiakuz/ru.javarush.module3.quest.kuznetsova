package ru.javarush.quest.service;

import ru.javarush.quest.model.dto.ChoiceOutDto;
import ru.javarush.quest.model.dto.QuestOutDto;
import ru.javarush.quest.model.dto.StepOutDto;
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
    public QuestOutDto getQuestById(long id) {
        return questRepository.getQuestById(id);
    }

    @Override
    public StepOutDto getStartStepByQuestId(long questId) {
        StepOutDto startStep = questRepository.getStartStepByQuestId(questId);

        List<ChoiceOutDto> choices = questRepository.getChoicesByStepId(startStep.getId());

        startStep.setChoices(choices);

        return startStep;
    }

    @Override
    public StepOutDto getStepById(long id) {
        StepOutDto nextStep = questRepository.getStepById(id);

        List<ChoiceOutDto> choices = questRepository.getChoicesByStepId(id);

        nextStep.setChoices(choices);

        return nextStep;
    }
}
