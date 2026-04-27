package com.fashion.controller;

import com.fashion.model.FashionItem;
import com.fashion.repository.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/items")
public class ItemController {

    @Autowired
    private ItemRepository itemRepository;

    @GetMapping
    public List<FashionItem> getAllItems() {
        return itemRepository.findAll();
    }

    @PostMapping
    public FashionItem createItem(@RequestBody FashionItem item) {
        return itemRepository.save(item);
    }

    @PutMapping("/{id}")
    public FashionItem updateItem(@PathVariable Long id, @RequestBody FashionItem itemDetails) {
        FashionItem item = itemRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Item not found with id: " + id));

        // Ці методи можуть бути червоними, доки не спрацює Lombok
        item.setName(itemDetails.getName());
        item.setDescription(itemDetails.getDescription());
        item.setBrand(itemDetails.getBrand());

        return itemRepository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        itemRepository.deleteById(id);
    }
}