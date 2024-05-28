package org.jp.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(schema = "catalogue",name="t_product")
@NamedQueries(
        @NamedQuery(
                name="Product.findAllByTitleLikeIgnoreCase",
                query="select * from catalogue.t_product  where c_title ilike :filter")//sql
)
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @Column(name="c_title")
    private String title;
    @Column(name="c_details")
    private String details;
    @Column(name="image")
    private String img;
    @Column(name="t_price")
    private float price;


}
