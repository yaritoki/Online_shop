package org.jp.client;

import org.jp.entity.Product;

import java.util.List;
import java.util.Optional;

public interface RestClientProduct {
    List<Product> findAllProducts(String filter);

    Product createProduct(String title, String details, String img,float price);

    Optional<Product> findById(int productId);

    void updateProduct(Integer productId, String title, String details, String img,float price);

   void deleteProduct(Integer productId);
}
