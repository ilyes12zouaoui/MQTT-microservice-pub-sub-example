package com.lass.products.commun.mapper;

import com.lass.products.commun.to.ProductTO;
import com.lass.products.persistence.ProductEntity;

public class ProductMapper {

    public static ProductTO toProductTO(ProductEntity productEntity) {
        if (productEntity == null) return null;
        return new ProductTO(productEntity.getId(), productEntity.getName(), productEntity.getCategoryId());
    }

    public static ProductEntity toProductEntity(ProductTO productTO) {
        if (productTO == null) return null;
        return new ProductEntity(productTO.getId(), productTO.getName(), productTO.getCategoryId());
    }

}
