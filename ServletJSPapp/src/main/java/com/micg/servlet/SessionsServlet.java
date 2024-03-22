package com.micg.servlet;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.service.AccountService;
import com.micg.servlet.utilities.ServletUtilities;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.IOException;

@WebServlet(urlPatterns = {"/"})
public class SessionsServlet extends HttpServlet {
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws ServletException, IOException {

        httpServletRequest.getRequestDispatcher("log.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Вход в систему
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String login = httpServletRequest.getParameter("login");
        String password = httpServletRequest.getParameter("password");

        if (login.isEmpty() || password.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserAccount profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.password().equals(password)) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Неправильный логин или пароль");
            return;
        }

        var session = httpServletRequest.getSession();
        session.setAttribute("login",login);
        session.setAttribute("pass", password);

        String currentURL = httpServletRequest.getRequestURL().toString();
        httpServletResponse.sendRedirect(ServletUtilities.makeRedirectUrl(currentURL, "/manager"));
    }
}
