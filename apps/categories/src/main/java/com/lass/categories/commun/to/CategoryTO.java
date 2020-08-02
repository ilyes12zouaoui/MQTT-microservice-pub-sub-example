package com.lass.categories.commun.to;


import com.fasterxml.jackson.annotation.JsonProperty;

public class CategoryTO {

    @JsonProperty("id")
    private Long id;

    @JsonProperty("name")
    private String name;

    public CategoryTO() {
    }

    public CategoryTO(Long id, String name) {
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
