package com.lass.categories.service;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.lass.categories.commun.mapper.CategoryMapper;
import com.lass.categories.commun.to.CategoryTO;
import com.lass.categories.mqtt.MQTTClientWrapper;
import com.lass.categories.mqtt.MQTTPublishWrapper;
import com.lass.categories.persistence.repository.CategoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
public class CategoryService {
    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    MQTTPublishWrapper mqttPublishWrapper;

    public void verifyExistenceById(Long id){
        categoryRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("no category was find for id " + id));
    }

    public void remove(Long id) {
        verifyExistenceById(id);
        categoryRepository.deleteById(id);
        mqttPublishWrapper.publish(id);
    }

    public CategoryTO create(CategoryTO categoryTO) {
        return CategoryMapper.toCategoryTO(categoryRepository.save(CategoryMapper.toCategoryEntity(categoryTO)));
    }

    public CategoryTO update(CategoryTO categoryTO) {
        verifyExistenceById(categoryTO.getId());
        return CategoryMapper.toCategoryTO(categoryRepository.save(CategoryMapper.toCategoryEntity(categoryTO)));
    }

    public CategoryTO getById(Long id) {
        return CategoryMapper
                .toCategoryTO(
                        categoryRepository.findById(id)
                                .orElseThrow(() -> new RuntimeException("no category was find for id " + id))
                );
    }

    public Page<CategoryTO> getByPage(Pageable pageable) {
        return categoryRepository.findAll(pageable).map(CategoryMapper::toCategoryTO);
    }

}
