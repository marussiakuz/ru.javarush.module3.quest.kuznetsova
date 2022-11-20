package ru.javarush.quest.repository;

import ru.javarush.quest.controller.listener.DataBaseConnection;
import ru.javarush.quest.exception.DataSourceIsNotAvailableException;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;

import javax.annotation.ManagedBean;
import javax.inject.Inject;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ManagedBean
public class QuestDbRepository implements IQuestRepository {

    @Inject
    private DataBaseConnection dataBaseConnection;

    @Override
    public List<QuestOutDto> getQuests() {
        String query = "select * from quest";

        List<QuestOutDto> quests = new ArrayList<>();

        try {
            Connection connection = dataBaseConnection.getDataSource().getConnection();
            Statement statement = connection.createStatement();
            ResultSet rs = statement.executeQuery(query);

            while (rs.next()) {
                QuestOutDto quest = QuestOutDto.builder()
                        .id(rs.getLong("quest_id"))
                        .name(rs.getString("quest_name"))
                        .description(rs.getString("description"))
                        .img(rs.getString("img"))
                        .build();
                quests.add(quest);
            }

            connection.close();
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException("an unknown error occurred while trying to get a list of quests");
        }

        return quests;
    }

    @Override
    public Optional<QuestOutDto> getQuestById(long id) {
        PreparedStatement preparedStatement;
        Optional<QuestOutDto> questOptional = Optional.empty();

        try {
            Connection connection = dataBaseConnection.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select * from quest where quest_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                questOptional = Optional.of(
                        QuestOutDto.builder()
                                .id(rs.getLong("quest_id"))
                                .img(rs.getString("img"))
                                .name(rs.getString("quest_name"))
                                .description(rs.getString("description"))
                                .build()
                );
            }

            connection.close();
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("an unknown error occurred while trying to get " +
                    "quest id=%s", id));
        }

        return questOptional;
    }

    @Override
    public Optional<StepOutDto> getStartStepByQuestId(long questId) {
        PreparedStatement preparedStatement;
        Optional<StepOutDto> stepOptional = Optional.empty();

        try {
            Connection connection = dataBaseConnection.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select step_id, question from step where is_start = " +
                    "true and quest_id = ?");
            preparedStatement.setLong(1, questId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                stepOptional = Optional.of(
                        StepOutDto.builder()
                                .id(rs.getLong("step_id"))
                                .question(rs.getString("question"))
                                .build()
                );
            }

            connection.close();
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("an unknown error occurred while trying to get " +
                    "start step by questId=%s", questId));
        }

        return stepOptional;
    }

    @Override
    public List<ChoiceOutDto> getChoicesByStepId(long stepId) {
        PreparedStatement preparedStatement;
        List<ChoiceOutDto> choices = new ArrayList<>();

        try {
            Connection connection = dataBaseConnection.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select choice.choice_id, answer, state, " +
                    "to_be_continue.next_step_id, the_end.text from choice left join to_be_continue using (choice_id) " +
                    "left join the_end using (choice_id) where current_step_id = ?");
            preparedStatement.setLong(1, stepId);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                choices.add(mapToChoiceOutDto(rs));
            }

            connection.close();
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("an unknown error occurred while trying to get " +
                    "a list of choices for the step with id=%s", stepId));
        }

        return choices;
    }

    @Override
    public Optional<StepOutDto> getStepById(long id) {
        PreparedStatement preparedStatement;
        Optional<StepOutDto> stepOptional = Optional.empty();

        try {
            Connection connection = dataBaseConnection.getDataSource().getConnection();
            preparedStatement = connection.prepareStatement("select step_id, question from step where step_id = ?");
            preparedStatement.setLong(1, id);
            ResultSet rs = preparedStatement.executeQuery();

            while (rs.next()) {
                stepOptional = Optional.of(
                        StepOutDto.builder()
                                .id(rs.getLong("step_id"))
                                .question(rs.getString("question"))
                                .build()
                );
            }

            connection.close();
        }
        catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("an unknown error occurred while trying to get " +
                    "step by id=%s", id));
        }

        return stepOptional;
    }

    private ChoiceOutDto mapToChoiceOutDto(ResultSet resultSet) throws SQLException {
        State state = State.valueOf(resultSet.getString("state"));
        ChoiceOutDto choice;

        if (state == State.CONTINUE) {
            choice = ContinueChoiceOutDto.builder()
                    .nextStepId(resultSet.getLong("next_step_id"))
                    .build();
        } else {
            choice = FinishedChoiceOutDto.builder()
                    .text(resultSet.getString("text"))
                    .build();
        }

        choice.setId(resultSet.getLong("choice_id"));
        choice.setAnswer(resultSet.getString("answer"));
        choice.setState(state);

        return choice;
    }
}
