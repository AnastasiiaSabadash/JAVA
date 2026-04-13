package com.lab.cosmetics.servlet;

import com.lab.cosmetics.model.Cosmetics;
import com.lab.cosmetics.service.CosmeticService;
import com.google.gson.Gson;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.List;

@WebServlet("/cosmetic")
public class CosmeticServlet extends HttpServlet {
    private CosmeticService cosmeticService = new CosmeticService();
    private Gson gson = new Gson();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        List<Cosmetics> cosmetics = cosmeticService.getAllCosmetics();
        resp.setContentType("application/json");
        resp.getWriter().write(gson.toJson(cosmetics));
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Cosmetics newCosmetic = gson.fromJson(reader, Cosmetics.class);
        cosmeticService.addCosmetic(newCosmetic);
        resp.setStatus(HttpServletResponse.SC_CREATED);
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        BufferedReader reader = req.getReader();
        Cosmetics updatedCosmetic = gson.fromJson(reader, Cosmetics.class);
        if (cosmeticService.updateCosmetic(updatedCosmetic)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        if (cosmeticService.deleteCosmetic(id)) {
            resp.setStatus(HttpServletResponse.SC_OK);
        } else {
            resp.setStatus(HttpServletResponse.SC_NOT_FOUND);
        }
    }
}