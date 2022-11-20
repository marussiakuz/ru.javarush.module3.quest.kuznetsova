package ru.javarush.quest.controller.listener;

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
        executeSqlScript(servletContextEvent, "/WEB-INF/sql/schema.sql");
        executeSqlScript(servletContextEvent, "/WEB-INF/sql/data.sql");
    }

    private void executeSqlScript(ServletContextEvent servletContextEvent, String file) {
        String sql = readStreamToString(servletContextEvent.getServletContext(), file);
        if (StringUtils.isBlank(sql)) {
            throw new DataSourceIsNotAvailableException(String.format("SQL script is empty, file: %s", file));
        }
        try {
            log.info("Executing SQL script");
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            throw new DataSourceIsNotAvailableException(String.format("Unable to execute SQL script: %s", e));
        }
    }

    private String readStreamToString(ServletContext servletContext, String file) {
        BufferedInputStream bis = new BufferedInputStream(servletContext.getResourceAsStream(file));
        ByteArrayOutputStream buf = new ByteArrayOutputStream();

        try {
            int result = bis.read();
            while(result != -1) {
                byte b = (byte)result;
                buf.write(b);
                result = bis.read();
            }
        } catch(IOException e) {
            throw new DataSourceIsNotAvailableException(String.format("Unable to read the file %s", file));
        }

        return buf.toString();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
