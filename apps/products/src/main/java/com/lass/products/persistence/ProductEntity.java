package com.lass.products.persistence;

import javax.persistence.*;

@Table(name = "products")
@Entity
@SequenceGenerator(name="seq", initialValue=5, allocationSize=100)
public class ProductEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    private String name;

    private Long categoryId;

    public ProductEntity() {
    }

    public ProductEntity(Long id,String name,Long categoryId) {
        this.id = id;
        this.name = name;
        this.categoryId = categoryId;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
