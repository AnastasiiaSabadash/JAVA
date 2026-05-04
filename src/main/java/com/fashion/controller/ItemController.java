package com.fashion.controller;

import com.fashion.model.FashionItem;
import com.fashion.repository.ItemRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<FashionItem> getAllItems() {
        log.info("Отримання списку товарів");
        return itemRepository.findAll();
    }

    @PostMapping
    public FashionItem createItem(@RequestBody FashionItem item) {
        log.info("Додавання нового товару: {}", item.getName());
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public FashionItem updateItem(@PathVariable Long id, @RequestBody FashionItem itemDetails) {
        log.info("Оновлення товару з id: {}", id);
        FashionItem item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setBrand(itemDetails.getBrand());

        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        log.info("Видалення товару з id: {}", id);
        itemRepository.deleteById(id);
    }
}