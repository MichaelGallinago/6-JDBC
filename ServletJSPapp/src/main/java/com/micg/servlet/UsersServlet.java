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

import static com.micg.servlet.service.FileService.userDirectoriesPath;

@WebServlet(urlPatterns = {"/registration"})
public class UsersServlet extends HttpServlet {

    public void doGet(HttpServletRequest httpServletRequest,
                      HttpServletResponse httpServletResponse) throws ServletException, IOException {
        httpServletRequest.getRequestDispatcher("registration.jsp").forward(httpServletRequest, httpServletResponse);
    }

    //Регистрация в системе
    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws IOException {
        String email = request.getParameter("email");
        String login = request.getParameter("login");
        String password = request.getParameter("password");

        if (email.isEmpty() || login.isEmpty() || password.isEmpty()) {
            response.setContentType("text/html;charset=utf-8");
            //response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
            response.getWriter().println("Отсутсвует email, логин или пароль");
            return;
        }

        if (AccountService.getUserByLogin(login) != null) {
            response.setContentType("text/html;charset=utf-8");
            //response.setStatus(HttpServletResponse.SC_CONFLICT);
            response.getWriter().println("Пользователь с таким логином уже есть, выберите другой логин");
            return;
        }

        UserAccount profile = new UserAccount(login, password, email);
        AccountService.addNewUser(profile);

        var session = request.getSession();
        session.setAttribute("login", login);
        session.setAttribute("password", password);

        // Создание новой папки для пользователя
        try {
            FileService.createDirectory(userDirectoriesPath + login);
        } catch (IOException e) {
            response.setContentType("text/html;charset=utf-8");
            response.getWriter().println(e.getMessage());
            return;
        }

        String currentURL = request.getRequestURL().toString();
        response.sendRedirect(ServletUtilities.makeRedirectUrl(currentURL, "/manager"));
    }
}
