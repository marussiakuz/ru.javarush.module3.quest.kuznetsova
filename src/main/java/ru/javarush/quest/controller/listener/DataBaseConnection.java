package ru.javarush.quest.controller.listener;

import javax.sql.DataSource;

import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import ru.javarush.quest.exception.DataSourceIsNotAvailableException;
import ru.javarush.quest.utils.SqlScriptExecutor;

import javax.annotation.Resource;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;

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

        SqlScriptExecutor.execute(dataSource, servletContextEvent
                .getServletContext()
                .getRealPath("/WEB-INF/sql/init.sql"));
    }
}
