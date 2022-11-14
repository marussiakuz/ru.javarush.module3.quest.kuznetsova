package ru.javarush.quest.repository;

import ru.javarush.quest.DataBase;
import ru.javarush.quest.model.quest.*;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ManagedBean
public class QuestDbRepository implements IQuestRepository {

    @Inject
    private DataBase dataBase;

    @Override
    public List<QuestOutDto> getQuests() {
        String query = "select quest_id, quest_name, description from quest";
        List<QuestOutDto> quests = new ArrayList<>();
        try {
            Connection connection = dataBase.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                QuestOutDto quest = QuestOutDto.builder()
                        .id(rs.getLong("quest_id"))
                        .name(rs.getString("quest_name"))
                        .description(rs.getString("description"))
                        .build();
                quests.add(quest);
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException");
        }
        return quests;
    }

    @Override
    public Optional<Quest> getQuestById() {
        return Optional.empty();
    }

    @Override
    public StepOutDto getStartStepByQuestId(long questId) {
        PreparedStatement preparedStatement;
        StepOutDto step = null;

        try {
            Connection connection = dataBase.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select step_id, question from step where is_start = " +
                    "true and quest_id = ?");
            preparedStatement.setLong(1, questId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                step = StepOutDto.builder()
                        .id(rs.getLong("step_id"))
                        .question(rs.getString("question"))
                        .build();
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException");
        }

        return step;
    }

    @Override
    public List<ChoiceOutDto> getChoicesByStepId(long stepId) {
        PreparedStatement preparedStatement;
        List<ChoiceOutDto> choices = new ArrayList<>();

        try {
            Connection connection = dataBase.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select choice_id, answer, state from choice where " +
                    "current_step_id = ?");
            preparedStatement.setLong(1, stepId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                ChoiceOutDto choice = ChoiceOutDto.builder()
                        .id(rs.getLong("choice_id"))
                        .answer(rs.getString("answer"))
                        .state(State.valueOf(rs.getString("state")))
                        .build();
                choices.add(choice);
            }
        }
        catch (SQLException e) {
            System.out.println("SQLException");
        }

        return choices;
    }
}
