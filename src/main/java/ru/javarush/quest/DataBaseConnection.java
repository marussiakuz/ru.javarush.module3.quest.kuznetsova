package ru.javarush.quest;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import ru.javarush.quest.exception.DataSourceIsNotAvailableException;

import javax.annotation.Resource;
import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.io.BufferedInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

@Slf4j
@Getter
@WebListener
public class DataBaseConnection implements ServletContextListener {

    @Resource(name="jdbc/db")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent servletContextEvent) {
        if (dataSource == null) {
            throw new DataSourceIsNotAvailableException("Data source wasn't initialized");
        }
        String sql = readStreamToString(servletContextEvent.getServletContext());
        if (StringUtils.isBlank(sql)) {
            throw new DataSourceIsNotAvailableException("SQL script is empty");
        }
        try {
            log.info("Executing SQL script");
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("Unable to create database table structure: %s",
                    e));
        }
    }

    private String readStreamToString(ServletContext servletContext) {
        BufferedInputStream bis = new BufferedInputStream(servletContext
                .getResourceAsStream("/WEB-INF/sql/schema.sql"));
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        try {
            int result = bis.read();
            while(result != -1) {
                byte b = (byte)result;
                buf.write(b);
                result = bis.read();
            }
        } catch(IOException e) {
            throw new DataSourceIsNotAvailableException("Unable to read the file");
        }

        return buf.toString();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
