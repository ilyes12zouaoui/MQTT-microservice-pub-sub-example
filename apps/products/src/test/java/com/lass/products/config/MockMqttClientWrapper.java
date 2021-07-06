package com.lass.products.config;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.hivemq.client.mqtt.datatypes.MqttQos;
import com.hivemq.client.mqtt.datatypes.MqttTopic;
import com.hivemq.client.mqtt.datatypes.MqttTopicFilter;
import com.lass.products.mqtt.MqttClientInterface;
import org.mockito.internal.matchers.CapturingMatcher;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;
import java.util.stream.Collectors;

@Service
@Primary
public class MockMqttClientWrapper implements MqttClientInterface {
    private final ObjectMapper objectMapper;

    private final List<Subscriber<?>> subscribers;
    private CapturingMatcher<MockMqttMessage> captor = new CapturingMatcher<>();

    public MockMqttClientWrapper(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
        subscribers = new ArrayList<>();
    }

    @Override
    public <T> void publish(String topic, MqttQos qos, T payload) {
        captor.captureFrom(new MockMqttMessage(topic, payload));
        subscribers.forEach(s -> s.dispatch(topic, payload));
    }

    public void publishPlain(String topic, String message) {
        captor.captureFrom(new MockMqttMessage(topic, message));
        subscribers.forEach(s -> {
            try {
                s.dispatch(topic, objectMapper.readValue(message, s.clazz));
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    @Override
    public <T> void subscribe(String topic, MqttQos qos, String shareGroupName, Class<T> payloadClass, Consumer<T> handler) {
        subscribers.add(new Subscriber<>(topic, payloadClass, handler));
    }

    public List<Object> getSentMessages(String topicFilter) {
        var filter = MqttTopicFilter.of(topicFilter);
        return captor.getAllValues().stream()
                .filter(m -> filter.matches(MqttTopic.of(m.topic)))
                .map(m -> m.payload)
                .collect(Collectors.toList());
    }

    public void reset() {
        captor = new CapturingMatcher<>();
    }

    private static class Subscriber<T> {
        final MqttTopicFilter topicFilter;
        final Class<T> clazz;
        final Consumer<T> handler;

        private Subscriber(String topicFilter, Class<T> clazz, Consumer<T> handler) {
            this.topicFilter = MqttTopicFilter.of(topicFilter);
            this.clazz = clazz;
            this.handler = handler;
        }

        void dispatch(String topic, Object payload) {
            if (topicFilter.matches(MqttTopicFilter.of(topic))) {
                if (clazz.isAssignableFrom(payload.getClass())) {
                    handler.accept((T) payload);
                } else {
                    throw new ClassCastException("Cannot map message of type " + payload.getClass().getCanonicalName() + " to handler on topic " + topic);
                }
            }
        }
    }

    private static class MockMqttMessage {
        public final String topic;
        public final Object payload;

        private MockMqttMessage(String topic, Object payload) {
            this.topic = topic;
            this.payload = payload;
        }
    }
}
