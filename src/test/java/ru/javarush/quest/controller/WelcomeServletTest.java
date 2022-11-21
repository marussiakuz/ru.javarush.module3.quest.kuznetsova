package ru.javarush.quest.controller;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static org.mockito.Mockito.doNothing;

@ExtendWith(MockitoExtension.class)
class WelcomeServletTest {

    @Mock
    private HttpServletRequest req;
    @Mock
    private HttpServletResponse resp;
    @Mock
    private ServletConfig servletConfig;
    @Mock
    private ServletContext servletContext;
    @Mock
    private RequestDispatcher requestDispatcher;
    @InjectMocks
    private WelcomeServlet welcomeServlet;

    @Test
    void whenDoGetThenForwardToWelcomeJsp() throws ServletException, IOException {
        Mockito
                .when(servletConfig.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(welcomeServlet.getServletContext())
                .thenReturn(servletContext);

        Mockito
                .when(servletContext.getRequestDispatcher(Mockito.anyString()))
                .thenReturn(requestDispatcher);

        doNothing()
                .when(requestDispatcher).forward(req, resp);

        welcomeServlet.doGet(req, resp);

        Mockito.verify(servletContext, Mockito.times(1))
                .getRequestDispatcher("/welcome.jsp");
        Mockito.verify(requestDispatcher, Mockito.times(1))
                .forward(req, resp);
    }
}