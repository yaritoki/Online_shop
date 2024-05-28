<<<<<<< HEAD
package org.jp.service;

import org.jp.entity.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> findAllProducts(String filter);

    Product createProduct(String title, String details, String img, float price);

    Optional<Product> findProduct(int productId);

    void deleteProduct(int productId);

    void updateProduct(int productId, String title, String details, String img,float price);
}
=======
package org.jp.service;

import org.jp.entity.Product;

import java.util.Optional;

public interface ProductService {

    Iterable<Product> findAllProducts();

    Product createProduct(String title, String details, String img, float price);

    Optional<Product> findProduct(int productId);

    void deleteProduct(int productId);

    void updateProduct(int productId, String title, String details, String img,float price);
}
>>>>>>> 121134a607736c29885f4209e238a91c0cae2796
