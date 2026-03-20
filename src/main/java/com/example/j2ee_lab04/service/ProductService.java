package com.example.j2ee_lab04.service;

import com.example.j2ee_lab04.model.Category;
import com.example.j2ee_lab04.model.Product;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    private final List<Product> products = new ArrayList<>();

    public ProductService() {
        Category phone = new Category(1, "Dien thoai");
        Category laptop = new Category(2, "Laptop");
        products.add(new Product(1, "iPhone 15", null, 25000000L, phone));
        products.add(new Product(2, "MacBook Air M3", null, 32000000L, laptop));
    }

    public List<Product> getAll() {
        return products;
    }

    public Product get(int id) {
        return products.stream()
                .filter(product -> product.getId() == id)
                .findFirst()
                .orElse(null);
    }

    public void add(Product product) {
        product.setId(products.stream().mapToInt(Product::getId).max().orElse(0) + 1);
        products.add(product);
    }

    public void update(Product editProduct) {
        Product existingProduct = get(editProduct.getId());
        if (existingProduct == null) {
            return;
        }

        existingProduct.setName(editProduct.getName());
        existingProduct.setPrice(editProduct.getPrice());
        existingProduct.setCategory(editProduct.getCategory());
        existingProduct.setImage(editProduct.getImage());
    }

    public void delete(int id) {
        products.removeIf(product -> product.getId() == id);
    }
}
