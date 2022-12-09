package ru.javarush.quest.utils;

import lombok.extern.slf4j.Slf4j;
import ru.javarush.quest.exception.DataSourceIsNotAvailableException;

import javax.sql.DataSource;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
public class SqlScriptExecutor {

    private SqlScriptExecutor() {

    }

    public static void execute(DataSource dataSource, String file) {
        String sql;

        try {
            sql = Files.readString(Path.of(file));
        } catch (IOException e) {
            throw new DataSourceIsNotAvailableException(String.format("Unable to read the file %s", file));
        }

        if (sql.isBlank()) {
            throw new DataSourceIsNotAvailableException(String.format("SQL script is empty, file: %s", file));
        }

        try (Connection connection = dataSource.getConnection();
             Statement statement = connection.createStatement()) {
            log.info("Executing SQL script");
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("Unable to execute SQL script: %s", e.getMessage()));
        }
    }
}
