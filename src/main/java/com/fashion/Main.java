package com.fashion;

import com.fashion.dao.FashionDAO;
import com.fashion.model.FashionItem;

import java.util.List;

public class Main {
    public static void main(String[] args) {
        FashionDAO fashionDAO = new FashionDAO();

        // Додавання об'єкта
        FashionItem item = new FashionItem("Літня сукня", "Легка сукня з льону", "Zara");
        fashionDAO.saveFashionItem(item);
        System.out.println("Товар доданий: " + item);

        // Отримання всіх товарів
        List<FashionItem> items = fashionDAO.getAllFashionItems();
        System.out.println("Список товарів: " + items);

        // Оновлення товару
        item.setName("Оновлена сукня");
        fashionDAO.updateFashionItem(item);
        System.out.println("Оновлений товар: " + fashionDAO.getFashionItemById(item.getId()));

        // Видалення товару
        fashionDAO.deleteFashionItem(item.getId());
        System.out.println("Товар видалений");
    }
}
