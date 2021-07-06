In order to learn more about microservice asynchronous communication using events with event-broker, I passionatly created this project where I use 
MQTT event broker to enable asynchronous communication between two simple microservices product and category, business case is when a category is deleted an event is published to the broker, and the
product is subscribing to that event to delete all products that has belong to that category.

- In the main branch second commit I extracted commun code for publishing and subscribing to events into a jar lib, which you can published to a remote artifact repository and then import it
in the product and category spring boot projects.

- For integration test instead of using an event broker for integration tests, I created a mock for it using a stateful spring bean that can be found on the branch 'mock_mqtt_broker_for_test' in the product microervice.

