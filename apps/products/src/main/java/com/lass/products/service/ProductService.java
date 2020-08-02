package com.lass.products.service;

import com.lass.products.commun.mapper.ProductMapper;
import com.lass.products.commun.to.ProductTO;
import com.lass.products.mqtt.MQTTSubscribeWrapper;
import com.lass.products.persistence.ProductEntity;
import com.lass.products.persistence.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProductService {
    @Autowired
    ProductRepository productRepository;

    public void verifyExistenceById(Long id){
        productRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no product was find for id " + id));
    }

    public void remove(Long id) {
        verifyExistenceById(id);
        productRepository.deleteById(id);
    }

    public ProductTO create(ProductTO productTO) {
        return ProductMapper.toProductTO(productRepository.save(ProductMapper.toProductEntity(productTO)));
    }

    public ProductTO update(ProductTO productTO) {
        verifyExistenceById(productTO.getId());
        return ProductMapper.toProductTO(productRepository.save(ProductMapper.toProductEntity(productTO)));
    }

    public ProductTO getById(Long id) {
        return ProductMapper
                .toProductTO(
                        productRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("no product was find for id " + id))
                );
    }

    public Page<ProductTO> getByPage(Pageable pageable) {
        return productRepository.findAll(pageable).map(ProductMapper::toProductTO);
    }

    public void deleteByCategoryId(Long categoryId){
        List<ProductEntity> productEntities = productRepository.getAllByCategoryId(categoryId);
        productRepository.deleteAll(productEntities);
    }
}
