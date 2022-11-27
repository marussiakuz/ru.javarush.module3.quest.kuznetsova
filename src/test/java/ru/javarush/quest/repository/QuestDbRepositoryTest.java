package ru.javarush.quest.repository;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javarush.quest.controller.listener.DataBaseConnection;
import ru.javarush.quest.model.dto.*;
import ru.javarush.quest.model.enums.State;

import javax.sql.DataSource;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class QuestDbRepositoryTest {

    @Mock
    private DataBaseConnection dataBaseConnection;
    @Mock
    private DataSource dataSource;
    @InjectMocks
    private QuestDbRepository questDbRepository;
    private Connection connection;

    @BeforeEach
    void setUp() throws SQLException {
        try {
            connection = createDataBaseConnection();
        } catch (SQLException e) {
            log.error("Connection failed: {}", e.getMessage());
        }

        try {
            prepareDataBase(connection);
        } catch (IOException e) {
            log.error("failed to execute init SQL script: {}", e.getMessage());
        }

        Mockito
                .when(dataBaseConnection.getDataSource())
                .thenReturn(dataSource);

        Mockito
                .when(dataSource.getConnection())
                .thenReturn(connection);
    }

    @AfterEach
    void afterEach() {
        try {
            connection.close();
        } catch (SQLException e) {
            log.error("Connection couldn't be closed: {}", e.getMessage());
        }
    }

    @Test
    void whenGetQuestsThenReturnAllQuests() {
        List<QuestOutDto> quests = questDbRepository.getQuests();

        assertFalse(quests.isEmpty());

        assertEquals(2, quests.size());

        assertEquals(1, quests.get(0).getId());
        assertEquals("Quest One", quests.get(0).getName());
        assertEquals("Some description", quests.get(0).getDescription());
        assertEquals("resources/questOne.gif", quests.get(0).getImg());
        assertEquals(2, quests.get(1).getId());
        assertEquals("Quest Two", quests.get(1).getName());
        assertEquals("Very strange quest", quests.get(1).getDescription());
        assertEquals("resources/questTwo.gif", quests.get(1).getImg());
    }

    @Test
    void whenGetQuestByIdThenReturnOptionalOfExistsQuest() {
        Optional<QuestOutDto> questOptional = questDbRepository.getQuestById(1L);

        assertTrue(questOptional.isPresent());

        QuestOutDto quest = questOptional.get();

        assertEquals(1, quest.getId());
        assertEquals("Quest One", quest.getName());
        assertEquals("Some description", quest.getDescription());
        assertEquals("resources/questOne.gif", quest.getImg());
    }

    @Test
    void getQuestByIdIfQuestDoesntExistThenReturnEmptyOptional() {
        Optional<QuestOutDto> questOptional = questDbRepository.getQuestById(111L);

        assertTrue(questOptional.isEmpty());
    }

    @Test
    void whenGetStartStepByQuestIdThenReturnOptionalOfExistsStep() {
        Optional<StepOutDto> stepOptional = questDbRepository.getStartStepByQuestId(2L);

        assertTrue(stepOptional.isPresent());

        StepOutDto step = stepOptional.get();

        assertEquals("Question on step One and Quest Two", step.getQuestion());
    }

    @Test
    void getStartStepByQuestIdIfStepDoesntExistThenReturnEmptyOptional() {
        Optional<StepOutDto> stepOptional = questDbRepository.getStartStepByQuestId(111L);

        assertTrue(stepOptional.isEmpty());
    }

    @Test
    void getChoicesByStepIdIfReturnFailureContinueAndWinChoices() {
        List<ChoiceOutDto> choices = questDbRepository.getChoicesByStepId(1);

        assertFalse(choices.isEmpty());

        assertEquals(3, choices.size());

        assertEquals("Failure choice on step One and Quest One", choices.get(0).getAnswer());
        assertEquals(State.FAILURE, choices.get(0).getState());
        assertSame(FinishedChoiceOutDto.class, choices.get(0).getClass());
        assertEquals("Choice with continue on step One and Quest One", choices.get(1).getAnswer());
        assertEquals(State.CONTINUE, choices.get(1).getState());
        assertSame(ContinueChoiceOutDto.class, choices.get(1).getClass());
        assertEquals("Win choice on step One and Quest One", choices.get(2).getAnswer());
        assertEquals(State.WIN, choices.get(2).getState());
        assertSame(FinishedChoiceOutDto.class, choices.get(2).getClass());
    }

    @Test
    void getChoicesByStepIdIfStepDoesntExistThenReturnEmptyList() {
        List<ChoiceOutDto> choices = questDbRepository.getChoicesByStepId(111);

        assertTrue(choices.isEmpty());
    }

    @Test
    void whenGetStepByIdThenReturnOptionalOfExistsStep() {
        Optional<StepOutDto> stepOptional = questDbRepository.getStepById(1L);

        assertTrue(stepOptional.isPresent());

        StepOutDto step = stepOptional.get();

        assertEquals(1, step.getId());
        assertEquals("Question on step One and Quest One", step.getQuestion());
    }

    @Test
    void getStepByIdIfStepDoesntExistThenReturnsEmptyOptional() {
        Optional<StepOutDto> stepOptional = questDbRepository.getStepById(111L);

        assertTrue(stepOptional.isEmpty());
    }

    private void prepareDataBase(Connection connection) throws SQLException, IOException {
        Path path = Path.of("src/test/resources/test_init.sql");
        Statement statement = connection.createStatement();
        statement.execute(Files.readString(path.toAbsolutePath()));
    }

    private Connection createDataBaseConnection() throws SQLException {
        JdbcDataSource ds = new JdbcDataSource();

        ds.setURL("jdbc:h2:mem:db");
        ds.setUser("test");
        ds.setPassword("test");

        return ds.getConnection("test", "test");
    }
}