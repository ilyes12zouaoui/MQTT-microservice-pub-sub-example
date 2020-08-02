package com.lass.categories.commun.mapper;

import com.lass.categories.commun.to.CategoryTO;
import com.lass.categories.persistence.CategoryEntity;

public class CategoryMapper {

    public static CategoryTO toCategoryTO(CategoryEntity categoryEntity) {
        if (categoryEntity == null) return null;
        return new CategoryTO(categoryEntity.getId(), categoryEntity.getName());
    }

    public static CategoryEntity toCategoryEntity(CategoryTO categoryTO) {
        if (categoryTO == null) return null;
        return new CategoryEntity(categoryTO.getId(), categoryTO.getName());
    }

}
