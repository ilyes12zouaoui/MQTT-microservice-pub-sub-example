package com.lass.categories.persistence;

import javax.persistence.*;

@Table(name = "categories")
@Entity
@SequenceGenerator(name="seq", initialValue=5, allocationSize=100)
public class CategoryEntity {

    @Id
    @GeneratedValue(strategy=GenerationType.SEQUENCE, generator="seq")
    private Long id;

    private String name;

    public CategoryEntity() {
    }

    public CategoryEntity(Long id,String name) {
        this.id = id;
        this.name = name;
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
