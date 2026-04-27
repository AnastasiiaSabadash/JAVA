package com.fashion;

import com.fashion.model.FashionItem;
import com.fashion.ItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "http://localhost:8080") // Щоб VS Code міг підключитися
@RestController // ЗМІНЕНО: тепер він віддає JSON, а не HTML
@RequestMapping("/items") // Шлях для фронтенду
public class ItemMvcController {

    @Autowired
    private ItemRepository repository;

    // Отримати всі товари
    @GetMapping
    public List<FashionItem> getAllItems() {
        return repository.findAll();
    }

    // Отримати один товар за ID
    @GetMapping("/{id}")
    public FashionItem getItemById(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

    // Зберегти товар (створення та оновлення)
    @PostMapping
    public FashionItem saveItem(@RequestBody FashionItem item) {
        return repository.save(item);
    }

    // Видалити товар
    @DeleteMapping("/{id}")
    public void deleteItem(@PathVariable Long id) {
        this.repository.deleteById(id);
    }
}