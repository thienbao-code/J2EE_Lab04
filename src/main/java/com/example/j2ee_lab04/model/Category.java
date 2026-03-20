package com.example.j2ee_lab04.model;

import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    private Integer id;

    @NotBlank(message = "Ten danh muc khong duoc de trong")
    private String name;
}
