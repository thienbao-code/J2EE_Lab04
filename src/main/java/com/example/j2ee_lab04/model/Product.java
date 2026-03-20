package com.example.j2ee_lab04.model;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private int id;

    @NotBlank(message = "Ten san pham khong duoc de trong")
    private String name;

    @Size(max = 200, message = "Ten hinh anh khong duoc vuot qua 200 ky tu")
    private String image;

    @NotNull(message = "Gia san pham khong duoc de trong")
    @Min(value = 1, message = "Gia san pham khong duoc nho hon 1")
    @Max(value = 9999999, message = "Gia san pham khong duoc lon hon 9999999")
    private Long price;

    private Category category = new Category();
}
