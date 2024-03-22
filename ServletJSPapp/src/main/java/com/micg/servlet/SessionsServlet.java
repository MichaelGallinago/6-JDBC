package com.micg.servlet;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.service.AccountService;
import com.micg.servlet.service.FileService;
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
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (login.isEmpty() || password.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Отсутсвует логин или пароль");
            return;
        }

        UserAccount profile = AccountService.getUserByLogin(login);
        if (profile == null || !profile.password().equals(password)) {
            response.setContentType("text/html;charset=utf-8");
            //response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().println("Неправильный логин или пароль");
            return;
        }

        var session = request.getSession();
        session.setAttribute("login",login);
        session.setAttribute("password", password);

        String currentURL = request.getRequestURL().toString();
        response.sendRedirect(ServletUtilities.makeRedirectUrl(currentURL, "/manager"));
    }
}
