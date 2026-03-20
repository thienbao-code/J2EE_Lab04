package com.example.j2ee_lab04.controller;

import com.example.j2ee_lab04.model.Category;
import com.example.j2ee_lab04.service.CategoryService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/categories")
public class CategoryController {

    private final CategoryService categoryService;

    public CategoryController(CategoryService categoryService) {
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "category/categories";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("category", new Category());
        return "category/create";
    }

    @PostMapping("/create")
    public String create(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/create";
        }

        categoryService.add(category);
        return "redirect:/categories";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable Integer id, Model model) {
        Category category = categoryService.get(id);
        if (category == null) {
            return "redirect:/categories";
        }

        model.addAttribute("category", category);
        return "category/edit";
    }

    @PostMapping("/edit")
    public String edit(@Valid Category category, BindingResult result) {
        if (result.hasErrors()) {
            return "category/edit";
        }

        categoryService.update(category);
        return "redirect:/categories";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable Integer id) {
        categoryService.delete(id);
        return "redirect:/categories";
    }
}
