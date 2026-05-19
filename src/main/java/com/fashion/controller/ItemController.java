package com.fashion.controller;

import com.fashion.model.FashionItem;
import com.fashion.repository.ItemRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/items")
@RequiredArgsConstructor
public class ItemController {

    private final ItemRepository itemRepository;

    // 1. Дозволяємо перегляд усім автентифікованим користувачам
    @GetMapping
    @PreAuthorize("isAuthenticated()")
    public List<FashionItem> getAllItems() {
        log.info("Отримання списку товарів");
        return itemRepository.findAll();
    }

    // 2. Дозволяємо створення усім автентифікованим користувачам
    @PostMapping
    @PreAuthorize("isAuthenticated()")
    public FashionItem createItem(@RequestBody FashionItem item) {
        log.info("Додавання нового товару: {}", item.getName());
        return itemRepository.save(item);
    }

    // 3. Дозволяємо оновлення усім автентифікованим користувачам
    @PutMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public FashionItem updateItem(@PathVariable Long id, @RequestBody FashionItem itemDetails) {
        log.info("Оновлення товару з id: {}", id);
        FashionItem item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setBrand(itemDetails.getBrand());

        return itemRepository.save(item);
    }

    // 4. Дозволяємо видалення усім автентифікованим користувачам
    @DeleteMapping("/{id}")
    @PreAuthorize("isAuthenticated()")
    public void deleteItem(@PathVariable Long id) {
        log.info("Видалення товару з id: {}", id);
        itemRepository.deleteById(id);
    }
}