package com.micg.servlet;

import com.micg.servlet.model.UserAccount;
import com.micg.servlet.service.AccountService;
import com.micg.servlet.utilities.ServletUtilities;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;

@WebServlet(urlPatterns = {"/registration"})
public class UsersServlet extends HttpServlet {

    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("registration.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Регистрация в системе
    public void doPost(HttpServletRequest httpServletRequest,
                       HttpServletResponse httpServletResponse) throws IOException {
        String email = httpServletRequest.getParameter("email");
        String login = httpServletRequest.getParameter("login");
        String password = httpServletRequest.getParameter("password");

        if (email.isEmpty() || login.isEmpty() || password.isEmpty()) {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        UserAccount profile = new UserAccount(login, password, email);
        if (AccountService.getUserByLogin(login) == null) {
            AccountService.addNewUser(profile);

            var session = httpServletRequest.getSession();
            session.setAttribute("login", login);
            session.setAttribute("pass", password);

            // Создание новой папки для пользователя
            File folder = new File("C:\\Users\\micha\\fileManager\\" + login);

            if (!folder.exists() && !folder.mkdir()) {
                httpServletResponse.setContentType("text/html;charset=utf-8");
                httpServletResponse.getWriter().println("Случилась ошибка при создании папки, попробуйте ещё раз");
                return;
            }

            String currentURL = httpServletRequest.getRequestURL().toString();
            httpServletResponse.sendRedirect(ServletUtilities.makeRedirectUrl(currentURL, "/manager"));
        } else {
            httpServletResponse.setContentType("text/html;charset=utf-8");
            httpServletResponse.getWriter().println("Пользователь с таким логином уже есть в системе");
        }
    }
}
