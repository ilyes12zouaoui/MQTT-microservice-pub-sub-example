package com.lass.products.mqtt;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.mqtt5.Mqtt5BlockingClient;
import com.hivemq.client.mqtt.mqtt5.Mqtt5Client;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAck;
import com.hivemq.client.mqtt.mqtt5.message.connect.connack.Mqtt5ConnAckReasonCode;
import com.hivemq.client.mqtt.mqtt5.message.publish.Mqtt5Publish;
import io.reactivex.plugins.RxJavaPlugins;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.UUID;
import java.util.concurrent.ExecutionException;
import java.util.function.Consumer;


@Service
public class MQTTClientWrapper {
    private static final Logger LOGGER = LoggerFactory.getLogger(MQTTClientWrapper.class);
    private final Mqtt5BlockingClient client;
    private final ObjectMapper om;

    public MQTTClientWrapper(ObjectMapper om, MQTTConfigurationProperties configurations) {
        this.om = om;

        RxJavaPlugins.setErrorHandler(e -> LOGGER.error("Exception with MQTT: " + e.getMessage(), e));
        this.client = Mqtt5Client.builder()
                .serverHost(configurations.getHost())
                .serverPort(configurations.getPort())
                .identifier(configurations.getClientId() + UUID.randomUUID().toString())
                .buildBlocking();

        Mqtt5ConnAck connResponse;
        if (configurations.getPersistence())
            connResponse = client.connectWith()
                    .cleanStart(false)
                    .sessionExpiryInterval(7/*days*/ * 24 * 60 * 60)
                    .send();
        else
            connResponse = client.connectWith()
                    .cleanStart(true)
                    .send();

        if (connResponse.getReasonCode() == Mqtt5ConnAckReasonCode.SUCCESS)
            LOGGER.info("connected to mqtt at {}:{} as {}", configurations.getHost(), configurations.getPort(), connResponse.getAssignedClientIdentifier());
        else {
            LOGGER.error("could not connect ot mqtt: {}, {}", connResponse.getReasonCode(), connResponse.getReasonString());
            throw new RuntimeException("could not connect to mqtt broker");
        }
    }

    public <T> void publish(String topic, MqttQos qos, T payload) {

        client.toBlocking()
                .publishWith()
                .topic(topic)
                .payload(serialize(payload))
                .qos(qos)
                .send()
                .getError()
                .ifPresent(e -> {
                    throw e instanceof RuntimeException ? (RuntimeException) e : new RuntimeException(e);
                });
    }

    private <T> byte[] serialize(T payload) {
        try {
            return om.writeValueAsBytes(payload);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public <T> void subscribe(String topic, MqttQos qos, String shareGroupName, Class<T> payloadClass, Consumer<T> handler) {
        var topicFilter = shareGroupName != null ? "$share/" + shareGroupName + "/" + topic : topic;

        try {
            client.toAsync()
                    .subscribeWith()
                    .topicFilter(topicFilter)
                    .qos(qos)
                    .callback(messageHandler(topic, payloadClass, handler))
                    .send()
                    .get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    private <T> Consumer<Mqtt5Publish> messageHandler(String topic, Class<T> payloadClass, Consumer<T> handler) {
        return mqtt5Publish -> {
            T payload;
            try {
                payload = om.readValue(mqtt5Publish.getPayloadAsBytes(), payloadClass);
            } catch (IOException e) {
                LOGGER.error("Error Parsing Message from " + topic, e);
                return;
            }
            try {
                handler.accept(payload);
            } catch (Throwable e) {
                LOGGER.error("Error handling " + topic + " event: " + e.getMessage(), new RuntimeException(e));
                return;
            }
        };
    }
}

