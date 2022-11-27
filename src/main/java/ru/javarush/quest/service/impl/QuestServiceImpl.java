package ru.javarush.quest.service.impl;

import ru.javarush.quest.exception.QuestNotFoundException;
import ru.javarush.quest.exception.StepNotFoundException;
import ru.javarush.quest.model.dto.ChoiceOutDto;
import ru.javarush.quest.model.dto.QuestOutDto;
import ru.javarush.quest.model.dto.StepOutDto;
import ru.javarush.quest.repository.QuestRepository;
import ru.javarush.quest.service.QuestService;

import javax.annotation.ManagedBean;
import javax.inject.Inject;

import java.util.List;

@ManagedBean
public class QuestServiceImpl implements QuestService {

    @Inject
    private QuestRepository questRepository;

    @Override
    public List<QuestOutDto> getQuests() {
        return questRepository.getQuests();
    }

    @Override
    public QuestOutDto getQuestById(long id) {
        return questRepository.getQuestById(id)
                .orElseThrow(() -> new QuestNotFoundException(String.format("quest with id=%s not found", id)));
    }

    @Override
    public StepOutDto getStartStepByQuestId(long questId) {
        StepOutDto startStep = questRepository.getStartStepByQuestId(questId)
                .orElseThrow(() ->
                        new StepNotFoundException(String.format("start step of quest with id=%s not found", questId)));

        List<ChoiceOutDto> choices = questRepository.getChoicesByStepId(startStep.getId());

        if (choices.isEmpty()) {
            throw new StepNotFoundException(String.format("start step of quest with id=%s has no any choices", questId));
        }

        startStep.setChoices(choices);

        return startStep;
    }

    @Override
    public StepOutDto getStepById(long id) {
        StepOutDto nextStep = questRepository.getStepById(id)
                .orElseThrow(() ->
                        new StepNotFoundException(String.format("step with id=%s not found", id)));

        List<ChoiceOutDto> choices = questRepository.getChoicesByStepId(id);

        if (choices.isEmpty()) {
            throw new StepNotFoundException(String.format("step with id=%s has no any choices", id));
        }

        nextStep.setChoices(choices);

        return nextStep;
    }
}
