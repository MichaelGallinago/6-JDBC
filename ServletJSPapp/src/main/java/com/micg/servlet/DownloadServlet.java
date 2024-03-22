package com.micg.servlet;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@WebServlet(urlPatterns = {"/downloadServlet"})
public class DownloadServlet extends HttpServlet {

    @Override
    public void doGet(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws IOException {

        String currentFilePath = httpServletRequest.getParameter("path");
        try (FileInputStream fileInputStream = new FileInputStream(currentFilePath)) {

            httpServletResponse.setHeader("Content-Disposition",
                    "attachment; filename=\"" + new File(currentFilePath).getName() + "\"");

            byte[] buffer = new byte[4096];
            int bytesRead;

            while ((bytesRead = fileInputStream.read(buffer)) != -1) {
                httpServletResponse.getOutputStream().write(buffer, 0, bytesRead);
            }
        }
    }
}
