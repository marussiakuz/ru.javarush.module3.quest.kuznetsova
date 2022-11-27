package ru.javarush.quest.controller.listener;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.*;
import org.mockito.junit.jupiter.MockitoExtension;

import ru.javarush.quest.exception.DataSourceIsNotAvailableException;
import ru.javarush.quest.utils.SqlScriptExecutor;

import javax.servlet.ServletContext;
import javax.servlet.ServletContextEvent;
import javax.sql.DataSource;

@ExtendWith(MockitoExtension.class)
class DataBaseConnectionTest {

    @InjectMocks
    private DataBaseConnection dataBaseConnection;
    @Mock
    private DataSource dataSource;
    @Mock
    private ServletContextEvent servletContextEvent;
    @Mock
    private ServletContext servletContext;

    @Test
    void whenContextInitializedIfDataSourceIsNullThenThrowsDataSourceIsNotAvailableException() {
        DataBaseConnection connectionWithoutDataBase = new DataBaseConnection();

        final DataSourceIsNotAvailableException exception = Assertions.assertThrows(
                DataSourceIsNotAvailableException.class,
                () -> connectionWithoutDataBase.contextInitialized(servletContextEvent));

        Assertions.assertNull(connectionWithoutDataBase.getDataSource());

        Assertions.assertEquals("Data source wasn't initialized", exception.getMessage());
    }

    @Test
    void whenContextInitializedThenCallSqlScriptExecutor() {
        Mockito
                .when(servletContextEvent.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRealPath("/WEB-INF/sql/init.sql"))
                .thenReturn("/../WEB-INF/sql/init.sql");

        MockedStatic<SqlScriptExecutor> theMock = Mockito.mockStatic(SqlScriptExecutor.class);

        dataBaseConnection.contextInitialized(servletContextEvent);

        Mockito
                .verify(servletContextEvent, Mockito.times(1))
                .getServletContext();

        Mockito
                .verify(servletContext, Mockito.times(1))
                .getRealPath(Mockito.anyString());

        theMock.verify(() -> SqlScriptExecutor.execute(dataSource, "/../WEB-INF/sql/init.sql"));
    }
}