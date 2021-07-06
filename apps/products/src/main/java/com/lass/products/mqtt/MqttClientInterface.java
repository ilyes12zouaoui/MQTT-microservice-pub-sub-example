package com.lass.products.mqtt;

import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;

import java.util.function.Consumer;

public interface MqttClientInterface {
    public <T> void publish(String topic, MqttQos qos, T payload);
    public <T> void subscribe(String topic, MqttQos qos, String shareGroupName, Class<T> payloadClass, Consumer<T> handler);
}
