package org.jp.entity;

import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class Product {

    private Integer id;
    @Size(min=3,max=50, message = "{catalogue.products.update.errors.title_size_is_invalid}")
    private String title;
    @Size(max=1000, message = "{catalogue.products.update.errors.details_size_is_invalid}")
    private String details;
    private String img;
    private float price;
}
