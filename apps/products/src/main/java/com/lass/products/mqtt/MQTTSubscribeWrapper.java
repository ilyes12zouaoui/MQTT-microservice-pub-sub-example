package com.lass.products.mqtt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.lass.products.persistence.ProductEntity;
import com.lass.products.persistence.repository.ProductRepository;
import com.lass.products.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MQTTSubscribeWrapper {

    private ProductService productService;

    public MQTTSubscribeWrapper(MqttClientInterface mqttClientWrapper,ProductService productService) {
        this.productService = productService;
        mqttClientWrapper.subscribe("lass/categories/delete", MqttQos.AT_LEAST_ONCE, "product-service", DeleteCategoryMQTTPayload.class, this::handleDeleteCategory);
    }

    public void handleDeleteCategory(DeleteCategoryMQTTPayload payload) {
        productService.deleteByCategoryId(payload.categoryId);
    }


    public static class DeleteCategoryMQTTPayload {
        public final Long categoryId;

        @JsonCreator
        public DeleteCategoryMQTTPayload(@JsonProperty("categoryId") Long categoryId) {
            this.categoryId = categoryId;
        }
    }

}
