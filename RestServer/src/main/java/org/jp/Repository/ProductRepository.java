package org.jp.Repository;

import org.jp.entity.Product;
import org.springframework.data.repository.CrudRepository;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    Iterable<Product> findAll();
    Product save(Product product);

}
