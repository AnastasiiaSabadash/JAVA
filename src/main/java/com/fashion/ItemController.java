package com.fashion;

import com.fashion.model.FashionItem;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/api/items")
// Змінюємо тут, щоб дозволити запити з будь-якого порту (і 5173, і 5178)
@CrossOrigin(origins = "*")
public class ItemController {

    @Autowired
    private ItemRepository repository;

    @GetMapping
    public List<FashionItem> getAllItems() {
        return repository.findAll();
    }

    @PostMapping
    public FashionItem saveOrUpdateItem(@RequestBody FashionItem item) {
        return repository.save(item);
    }

    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        repository.deleteById(id);
    }
}