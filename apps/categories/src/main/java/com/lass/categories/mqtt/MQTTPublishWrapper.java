package com.lass.categories.mqtt;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MQTTPublishWrapper {

    @Autowired
    MQTTClientWrapper mqttClientWrapper;

    public void publish(Long id){
        var payload = new DeleteCategoryMQTTPayload(id);
        mqttClientWrapper.publish("lass/categories/delete", MqttQos.AT_LEAST_ONCE,payload);
    }


    public static class DeleteCategoryMQTTPayload{
        public final Long categoryId;

        @JsonCreator
        public DeleteCategoryMQTTPayload(@JsonProperty("categoryId") Long categoryId) {
            this.categoryId = categoryId;
        }
    }

}
