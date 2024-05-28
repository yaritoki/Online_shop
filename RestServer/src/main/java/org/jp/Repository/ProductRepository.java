package org.jp.Repository;

import org.jp.entity.Product;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;

public interface ProductRepository extends CrudRepository<Product,Integer> {
    @Query(name="Product.findAllByTitleLikeIgnoreCase",nativeQuery = true)
    Iterable<Product> findAllByTitleLikeIgnoreCase(@Param("filter") String filter);
    Iterable<Product> findAll();
    Product save(Product product);

}
