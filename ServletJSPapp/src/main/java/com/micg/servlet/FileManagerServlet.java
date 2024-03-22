package com.micg.servlet;

import com.micg.servlet.service.FileService;
import com.micg.servlet.utilities.ServletUtilities;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;

@WebServlet(urlPatterns = {"/manager"})
public class FileManagerServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest request, HttpServletResponse response)
            throws IOException, ServletException {

        String login = (String)request.getSession().getAttribute("login");
        String password = (String)request.getSession().getAttribute("password");
        if (login == null || password == null) {
            response.sendRedirect( "/login");
            return;
        }

        String pathToUserDir = FileService.userDirectoriesPath + login;
        String pathFromRequest = request.getParameter("path");

        String currentDirPath = pathFromRequest != null && pathFromRequest.startsWith(pathToUserDir) ?
                pathFromRequest : pathToUserDir;

        request.setAttribute("currentDirPath", currentDirPath);
        request.setAttribute("list", FileService.GetItemsFromDirectory(currentDirPath));

        String parentDirPath = new File(currentDirPath).getParent();
        if (parentDirPath == null) {
            parentDirPath = currentDirPath;
        }
        request.setAttribute("parentDirPath", parentDirPath);

        Date generationDate = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd.MM.yy HH:mm:ss");

        request.setAttribute("generationTime", dateFormat.format(generationDate));
        request.getRequestDispatcher("manager.jsp").forward(request, response);
    }

    public void doPost(HttpServletRequest request, HttpServletResponse httpServletResponse)
            throws IOException {

        var session = request.getSession();
        session.removeAttribute("login");
        session.removeAttribute("password");
        String currentURL = request.getRequestURL().toString();
        httpServletResponse.sendRedirect(ServletUtilities.makeRedirectUrl(currentURL, "/log"));
    }
}