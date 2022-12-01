package ru.javarush.quest.controller.filter;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import ru.javarush.quest.exception.PathVariableInvalidException;
import ru.javarush.quest.exception.UrlParameterInvalidException;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;

import java.io.IOException;

@ExtendWith(MockitoExtension.class)
class QuestFilterTest {

    @InjectMocks
    private QuestFilter questFilter;
    @Mock
    private HttpServletRequest servletRequest;
    @Mock
    private ServletResponse servletResponse;
    @Mock
    private FilterChain filterChain;

    @Test
    void doFilterIfParameterChoiceAndPathVariableQuestIdAreLongThenCallDoFilter() throws ServletException, IOException {
        setChoiceParamAndPathInfo("11", "quest/20");

        Mockito
                .doNothing().when(filterChain).doFilter(servletRequest, servletResponse);

        questFilter.doFilter(servletRequest, servletResponse, filterChain);

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.times(2))
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.times(1))
                .doFilter(servletRequest, servletResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = { "", "word", "\t", "\n", "2.5", "9999999999999999999999999999999999999999999999999999999" })
    void doFilterIfParameterChoiceIsNotLongThenThrowsUrlParameterInvalidException(String value)
            throws ServletException, IOException {
        Mockito
                .when(servletRequest.getParameter("choice"))
                .thenReturn(value);

        final UrlParameterInvalidException exception = Assertions.assertThrows(
                UrlParameterInvalidException.class,
                () -> questFilter.doFilter(servletRequest, servletResponse, filterChain));

        Assertions.assertEquals(String.format("param choice must be long value, but is %s", value),
                exception.getMessage());

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.never())
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.never())
                .doFilter(servletRequest, servletResponse);
    }

    @Test
    void doFilterIfParameterChoiceIsNullAndPathVariableQuestIdIsLongThenCallDoFilter() throws ServletException,
            IOException {
        setChoiceParamAndPathInfo(null, "quest/20");

        Mockito
                .doNothing().when(filterChain).doFilter(servletRequest, servletResponse);

        questFilter.doFilter(servletRequest, servletResponse, filterChain);

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.times(2))
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.times(1))
                .doFilter(servletRequest, servletResponse);
    }

    @Test
    void doFilterIfParameterChoiceAndPathVariableQuestIdBothAreNullThenCallDoFilter()
            throws ServletException, IOException {
        setChoiceParamAndPathInfo(null, null);

        Mockito
                .doNothing().when(filterChain).doFilter(servletRequest, servletResponse);

        questFilter.doFilter(servletRequest, servletResponse, filterChain);

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.times(1))
                .doFilter(servletRequest, servletResponse);
    }

    @ParameterizedTest
    @ValueSource(strings = {"word", "\t", "\n", " ", "2.5", "9999999999999999999999999999999999999999999999999999999" })
    void doFilterIfPathVariableQuestIdIsNotLongThenThrowsPathVariableInvalidException(String value)
            throws ServletException, IOException {
        setChoiceParamAndPathInfo("111", "quest/" + value);

        final PathVariableInvalidException exception = Assertions.assertThrows(
                PathVariableInvalidException.class,
                () -> questFilter.doFilter(servletRequest, servletResponse, filterChain));

        Assertions.assertEquals(String.format("path variable {questId} must be long value, but is %s", value),
                exception.getMessage());

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.times(2))
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.never())
                .doFilter(servletRequest, servletResponse);
    }

    @Test
    void doFilterIfPathVariableQuestIdIsEmptyThenThrowsPathVariableInvalidException()
            throws ServletException, IOException {
        setChoiceParamAndPathInfo("111", "quest/");

        final PathVariableInvalidException exception = Assertions.assertThrows(
                PathVariableInvalidException.class,
                () -> questFilter.doFilter(servletRequest, servletResponse, filterChain));

        Assertions.assertEquals("path variable {questId} cannot be blank or it must not be at all",
                exception.getMessage());

        Mockito
                .verify(servletRequest, Mockito.times(1))
                .getParameter("choice");

        Mockito
                .verify(servletRequest, Mockito.times(2))
                .getPathInfo();

        Mockito
                .verify(filterChain, Mockito.never())
                .doFilter(servletRequest, servletResponse);
    }

    private void setChoiceParamAndPathInfo(String choiceParam, String pathInfo) {
        Mockito
                .when(servletRequest.getParameter("choice"))
                .thenReturn(choiceParam);

        Mockito
                .when(servletRequest.getPathInfo())
                .thenReturn(pathInfo);
    }
}