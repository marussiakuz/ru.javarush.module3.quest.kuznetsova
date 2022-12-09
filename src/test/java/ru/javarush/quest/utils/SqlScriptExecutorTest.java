package ru.javarush.quest.utils;

import lombok.extern.slf4j.Slf4j;
import org.h2.jdbcx.JdbcDataSource;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javarush.quest.exception.DataSourceIsNotAvailableException;

import javax.sql.DataSource;

import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.*;

@Slf4j
@ExtendWith(MockitoExtension.class)
class SqlScriptExecutorTest {

    @Mock
    private DataSource dataSource;

    @Test
    void whenExecuteIfFileNotExistsThenThrowsDataSourceIsNotAvailableException() {
        String notExistsFile = "/WEB-INF/sql/not_exists.sql";

        final DataSourceIsNotAvailableException exception = Assertions.assertThrows(
                DataSourceIsNotAvailableException.class,
                () -> SqlScriptExecutor.execute(dataSource, notExistsFile));

        Assertions.assertTrue(Files.notExists(Path.of(notExistsFile).toAbsolutePath()));

        Assertions.assertEquals("Unable to read the file /WEB-INF/sql/not_exists.sql", exception.getMessage());
    }

    @Test
    void whenExecuteIfFileIsEmptyThenThrowsDataSourceIsNotAvailableException() {
        String emptySqlFile = "src/test/resources/test_empty.sql";

        final DataSourceIsNotAvailableException exception = Assertions.assertThrows(
                DataSourceIsNotAvailableException.class,
                () -> SqlScriptExecutor.execute(dataSource, emptySqlFile));

        Assertions.assertTrue(Files.exists(Path.of(emptySqlFile).toAbsolutePath()));

        Assertions.assertEquals("SQL script is empty, file: src/test/resources/test_empty.sql",
                exception.getMessage());
    }

    @Test
    void whenExecuteIfConnectionFailedThenThrowsDataSourceIsNotAvailableException() throws SQLException {
        Mockito
                .when(dataSource.getConnection())
                .thenReturn(getClosedConnection());

        String file = "src/test/resources/test_create_and_fill_table.sql";

        final DataSourceIsNotAvailableException exception = Assertions.assertThrows(
                DataSourceIsNotAvailableException.class,
                () -> SqlScriptExecutor.execute(dataSource, file));

        Assertions.assertTrue(Files.exists(Path.of(file).toAbsolutePath()));

        Assertions.assertTrue(exception.getMessage().contains("Unable to execute SQL script")
                && exception.getMessage().contains("The object is already closed"));
    }

    @Test
    void whenExecuteThenAddTableAndInsertRowToDataBase() {
        DataSource dataSource = getDataSource();
        String file = "src/test/resources/test_create_and_fill_table.sql";

        SqlScriptExecutor.execute(dataSource, file);

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            ResultSet resultSet = statement.executeQuery("select * from users");

            Assertions.assertNotNull(resultSet);
            Assertions.assertEquals(1, resultSet.getInt("id"));
            Assertions.assertEquals("JavaRush", resultSet.getString("name"));
            Assertions.assertEquals("cat123", resultSet.getString("password"));
        } catch (SQLException e) {
            log.error("an error occurred while executing the request: {}", e.getMessage());
        }
    }

    @Test
    void whenExecuteIfInvalidQueryThenThrowsDataSourceIsNotAvailableException() {
        DataSource dataSource = getDataSource();
        String file = "src/test/resources/test_invalid_query.sql";

        final DataSourceIsNotAvailableException exception = Assertions.assertThrows(
                DataSourceIsNotAvailableException.class,
                () -> SqlScriptExecutor.execute(dataSource, file));

        Assertions.assertTrue(Files.exists(Path.of(file).toAbsolutePath()));

        Assertions.assertEquals("Unable to execute SQL script: Table \"USERS\" not found (this database " +
                        "is empty); SQL statement:\n" +
                        "INSERT INTO users (name, password)\n" +
                        "VALUES ('JavaRush', 'cat123'); [42104-214]",
                exception.getMessage());
    }

    private DataSource getDataSource() {
        JdbcDataSource ds = new JdbcDataSource();

        ds.setURL("jdbc:h2:mem:test_db;DB_CLOSE_DELAY=-1");

        return ds;
    }

    private Connection getClosedConnection() throws SQLException {
        Connection connection = getDataSource().getConnection();
        connection.close();
        return connection;
    }
}