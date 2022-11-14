package ru.javarush.quest;

import javax.sql.DataSource;

import lombok.Getter;
import org.apache.commons.lang3.StringUtils;

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

@Getter
@WebListener
public class DataBase implements ServletContextListener {

    @Resource(name="jdbc/db")
    private DataSource dataSource;

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        if (dataSource == null) {
            System.out.println(("Data source wasn't initialized"));
        }
        String sql = readStreamToString(sce.getServletContext());
        if (StringUtils.isBlank(sql)) {
            System.out.println(("SQL script is empty"));
        }
        try {
            System.out.println(("Executing SQL script"));
            Connection connection = dataSource.getConnection();
            Statement statement = connection.createStatement();
            statement.execute(sql);
        } catch (SQLException e) {
            System.out.printf("Unable to create database table structure: %s", e);
        }
    }

    private String readStreamToString(ServletContext ctx) {
        BufferedInputStream bis = new BufferedInputStream(ctx.getResourceAsStream("/WEB-INF/sql/schema.sql"));
        ByteArrayOutputStream buf = new ByteArrayOutputStream();
        try {
            int result = bis.read();
            while(result != -1) {
                byte b = (byte)result;
                buf.write(b);
                result = bis.read();
            }
        }
        catch(IOException e) {
            System.out.println("IOException e");
        }

        return buf.toString();
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {

    }
}
