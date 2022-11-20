package ru.javarush.quest.controller.filter;

import ru.javarush.quest.exception.PathVariableInvalidException;
import ru.javarush.quest.exception.UrlParameterInvalidException;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

@WebFilter(servletNames = {"questServlet"})
public class QuestFilter implements Filter{

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain)
            throws IOException, ServletException {

        String choiceParameter = servletRequest.getParameter("choice");
        String questPathVariable = servletRequest instanceof HttpServletRequest
                && ((HttpServletRequest) servletRequest).getPathInfo() != null ?
                ((HttpServletRequest) servletRequest).getPathInfo().split("/")[1] : null;

        if (choiceParameter != null && isNotLong(choiceParameter)) {
            throw new UrlParameterInvalidException(String.format("param choice must be long value, but is %s",
                    choiceParameter));
        }

        if (questPathVariable != null && isNotLong(questPathVariable)) {
            throw new PathVariableInvalidException(String.format("Path variable {questId} must be long value, but is %s",
                    questPathVariable));
        }

        filterChain.doFilter(servletRequest, servletResponse);
    }

    private static boolean isNotLong(String str) {
        try {
            Long.parseLong(str);
            return false;
        } catch (NumberFormatException e) {
            return true;
        }
    }
}
