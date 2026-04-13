package com.lab.cosmetics.service;

import com.lab.cosmetics.model.Cosmetics;
import com.lab.cosmetics.utils.FileUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CosmeticService {
    private static final String FILE_PATH = "src/main/resources/data.json";
    private List<Cosmetics> cosmetics;

    public CosmeticService() {
        cosmetics = loadCosmetics();
    }

    public List<Cosmetics> getAllCosmetics() {
        return cosmetics;
    }

    public void addCosmetic(Cosmetics cosmetic) {
        int newId = cosmetics.isEmpty() ? 0 : cosmetics.get(cosmetics.size() - 1).getId() + 1;
        cosmetic.setId(newId);
        cosmetics.add(cosmetic);
        saveCosmetics();
    }

    public boolean updateCosmetic(Cosmetics updatedCosmetic) {
        for (int i = 0; i < cosmetics.size(); i++) {
            if (cosmetics.get(i).getId() == updatedCosmetic.getId()) {
                cosmetics.set(i, updatedCosmetic);
                saveCosmetics();
                return true;
            }
        }
        return false;
    }

    public boolean deleteCosmetic(int id) {
        boolean removed = cosmetics.removeIf(c -> c.getId() == id);
        if (removed) saveCosmetics();
        return removed;
    }

    private List<Cosmetics> loadCosmetics() {
        try {
            String json = FileUtil.readFromFile(FILE_PATH);
            return new Gson().fromJson(json, new TypeToken<List<Cosmetics>>() {}.getType());
        } catch (IOException e) {
            return new ArrayList<>();
        }
    }

    private void saveCosmetics() {
        try {
            String json = new Gson().toJson(cosmetics);
            FileUtil.writeToFile(FILE_PATH, json);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}