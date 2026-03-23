package com.lab.cosmetics;

import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import com.google.gson.Gson;

import java.io.IOException;


@WebServlet("/cosmetic")
public class CosmeticServlet extends HttpServlet {
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        Cosmetics cosmetics = new Cosmetics ("Гель-мус", "cosmetic.jpg", "Гель для очищення проблемної шкіри", 400.0, "Лікувальна");
        Gson gson = new Gson();
        String json = gson.toJson(cosmetics);
        resp.setContentType("application/json");
        resp.getWriter().write(json);
    }
}