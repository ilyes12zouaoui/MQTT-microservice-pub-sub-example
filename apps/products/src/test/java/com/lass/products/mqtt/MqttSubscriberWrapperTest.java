package com.lass.products.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.lass.products.config.MockMqttClientWrapper;
import com.lass.products.persistence.ProductEntity;
import com.lass.products.persistence.repository.ProductRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
public class MqttSubscriberWrapperTest {

    @Autowired
    MockMqttClientWrapper mockMqttClientWrapper;

    @Autowired
    ProductRepository productRepository;

    @Test
    public void handleDeleteCategory_correctCategory_succeed(){
        productRepository.saveAndFlush(new ProductEntity(5L,"name",123L));
        productRepository.saveAndFlush(new ProductEntity(6L,"name2",123L));
        mockMqttClientWrapper.publish("lass/categories/delete", MqttQos.AT_LEAST_ONCE,new MQTTSubscribeWrapper.DeleteCategoryMQTTPayload(123L));
        assertThat(productRepository.findAll()).extracting(ProductEntity::getCategoryId).doesNotContain(123L);
    }

}
