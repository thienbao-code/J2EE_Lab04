package com.example.j2ee_lab04.controller;

import com.example.j2ee_lab04.model.Category;
import com.example.j2ee_lab04.model.Product;
import com.example.j2ee_lab04.service.CategoryService;
import com.example.j2ee_lab04.service.ProductService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/products")
public class ProductController {

    private final ProductService productService;
    private final CategoryService categoryService;

    public ProductController(ProductService productService, CategoryService categoryService) {
        this.productService = productService;
        this.categoryService = categoryService;
    }

    @GetMapping
    public String index(Model model) {
        model.addAttribute("listproduct", productService.getAll());
        return "product/products";
    }

    @GetMapping("/create")
    public String create(Model model) {
        model.addAttribute("product", new Product());
        model.addAttribute("categories", categoryService.getAll());
        return "product/create";
    }

    @PostMapping("/create")
    public String create(
            @Valid Product newProduct,
            BindingResult result,
            @RequestParam(value = "category.id", required = false) Integer categoryId,
            Model model
    ) {
        if (newProduct.getImage() == null || newProduct.getImage().isBlank()) {
            result.rejectValue("image", "image.required", "Hinh anh khong duoc de trong");
        }

        if (categoryService.get(categoryId) == null) {
            result.rejectValue("category.id", "category.invalid", "Vui long chon danh muc hop le");
        }

        if (result.hasErrors()) {
            model.addAttribute("categories", categoryService.getAll());
            return "product/create";
        }

        newProduct.setCategory(categoryService.get(categoryId));
        productService.add(newProduct);
        return "redirect:/products";
    }

    @GetMapping("/edit/{id}")
    public String edit(@PathVariable int id, Model model) {
        Product product = productService.get(id);
        if (product == null) {
            return "redirect:/products";
        }

        model.addAttribute("product", product);
        model.addAttribute("categories", categoryService.getAll());
        return "product/edit";
    }

    @PostMapping("/edit")
    public String edit(
            @Valid Product editProduct,
            BindingResult result,
            @RequestParam(value = "category.id", required = false) Integer categoryId,
            Model model
    ) {
        Product existingProduct = productService.get(editProduct.getId());
        if (existingProduct == null) {
            return "redirect:/products";
        }

        Category selectedCategory = categoryService.get(categoryId);
        if (selectedCategory == null) {
            result.rejectValue("category.id", "category.invalid", "Vui long chon danh muc hop le");
        }

        if (result.hasErrors()) {
            if (editProduct.getImage() == null || editProduct.getImage().isBlank()) {
                editProduct.setImage(existingProduct.getImage());
            }
            model.addAttribute("categories", categoryService.getAll());
            return "product/edit";
        }

        editProduct.setCategory(selectedCategory);
        productService.update(editProduct);
        return "redirect:/products";
    }

    @PostMapping("/delete/{id}")
    public String delete(@PathVariable int id) {
        productService.delete(id);
        return "redirect:/products";
    }
}
