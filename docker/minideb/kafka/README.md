# Kafka image


Examples of legal listener lists: PLAINTEXT://myhost:9092,SSL://:9091 CLIENT://0.0.0.0:9092,REPLICATION://localhost:9093

Environment variables:

- `IS_ZOOKEEPER` is 1 if it is zookeeper node
- `IS_KAFKA` is 1 if it is the kafka node
- `ZOOKEEPER` is the ip of the zookeeper
- `LISTENERS` are the the internal listeners
- `ADVERTISED_LISTENERS` are the external listeners, defaults to $LISTENERS
- `AUTO_CREATE_TOPICS` to do autocreation of topics, defaults to true
- `KAFKA_ARGS` are args passed to kafka that can override any property
