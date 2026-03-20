package com.example.j2ee_lab04.service;

import com.example.j2ee_lab04.model.Category;
import jakarta.annotation.PostConstruct;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryService {

    private final List<Category> categories = new ArrayList<>();

    @PostConstruct
    public void init() {
        categories.add(new Category(1, "Dien thoai"));
        categories.add(new Category(2, "Laptop"));
        categories.add(new Category(3, "Phu kien"));
    }

    public List<Category> getAll() {
        return categories;
    }

    public Category get(Integer id) {
        if (id == null) {
            return null;
        }
        return categories.stream()
                .filter(category -> category.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Category category) {
        category.setId(categories.stream().mapToInt(Category::getId).max().orElse(0) + 1);
        categories.add(category);
    }

    public void update(Category editCategory) {
        Category existingCategory = get(editCategory.getId());
        if (existingCategory == null) {
            return;
        }

        existingCategory.setName(editCategory.getName());
    }

    public void delete(Integer id) {
        categories.removeIf(category -> category.getId().equals(id));
    }
}
