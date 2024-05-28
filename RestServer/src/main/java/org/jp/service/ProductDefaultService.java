package org.jp.service;

import lombok.RequiredArgsConstructor;
import org.jp.Repository.ProductRepository;
import org.jp.entity.Product;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ProductDefaultService implements ProductService{
    private final ProductRepository productRepository;

    @Override
    public Iterable<Product> findAllProducts(String filter) {
        if (filter!=null && filter.isBlank()) {
            return this.productRepository.findAllByTitleLikeIgnoreCase("%"+filter+"%");
        }else {
            return this.productRepository.findAll();
        }
    }

    @Override
    @Transactional
    public Product createProduct(String title, String details, String img, float price) {
        return  this.productRepository.save(new Product(null,title,details,img,price));
    }

    @Override
    public Optional<Product> findProduct(int productId) {
        return this.productRepository.findById(productId);
    }

    @Override
    @Transactional
    public void deleteProduct(int productId) {
        this.productRepository.deleteById(productId);
    }

    @Override
    @Transactional
    public void updateProduct(int id, String title, String details, String img,float price) {
        this.productRepository.findById(id)
                .ifPresentOrElse(
                product->{
                    product.setTitle(title);
                    product.setDetails(details);
                    product.setImg(img);
                    product.setPrice(price);
                },
                    ()->{
                    throw new NoSuchElementException();
                });
    }
}