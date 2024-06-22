# spring-boot-kafka-consumer


-- Install Docker Desktop and install kafka and zookeeper

-- 1. Create docker-compose.yaml file with script to create kafka and zookeeper
version: '3'
services:
  zookeeper:
    image: wurstmeister/zookeeper:3.4.6
    container_name: local_zookeeper
    ports:
      - "2181:2181"

  kafka:
    image: wurstmeister/kafka:2.13-2.7.0
    container_name: local_kafka
    ports:
      - "9092:9092"
    environment:
      KAFKA_BROKER_ID: 1
      KAFKA_ZOOKEEPER_CONNECT: zookeeper:2181
      KAFKA_LISTENERS: PLAINTEXT://0.0.0.0:9092
      KAFKA_ADVERTISED_LISTENERS: PLAINTEXT://localhost:9092
      KAFKA_LISTENER_SECURITY_PROTOCOL_MAP: PLAINTEXT:PLAINTEXT
      KAFKA_AUTO_CREATE_TOPICS_ENABLE: 'true'
    depends_on:
      - zookeeper
    volumes:
      - /var/run/docker.sock:/var/run/docker.sock

-- 2. Run Docker Compose to start zookeeper and kafka
docker-compose up -d

-- 3. Verify Kafka and Zookeeper:
docker-compose logs kafka
docker-compose logs zookeeper

-- 4. Interacting with Kafka:
	-- 4a. Creating a Topic:
	docker exec -it local_kafka kafka-topics.sh --create --topic my-topic --bootstrap-server localhost:9092 --partitions 1 --replication-factor 1

	-- 4b. Listing Topics:
	docker exec -it local_kafka kafka-topics.sh --list --bootstrap-server localhost:9092

	-- 4c. Produce messages:
	docker exec -it local_kafka kafka-console-producer.sh --topic my-topic --bootstrap-server localhost:9092

	-- Open new teminal for consumer
	-- Consuming Messages:
	docker exec -it local_kafka kafka-console-consumer.sh --topic my-topic --from-beginning --bootstrap-server localhost:9092

-- 5. Stopping and Removing Containers:
docker-compose down


----------------------------------------------------------------------------------------------------
-- Check the no.of partitions assigned to a topic (my-topic)
----------------------------------------------------------------------------------------------------
-- 1. To verify the change, you can describe the topic again
docker exec -it local_kafka /bin/bash

-- 2. kafka-topics.sh --describe --bootstrap-server localhost:9092 --topic your_topic_name

-- Output:
Topic: your_topic_name  PartitionCount: 3  ReplicationFactor: 1  Configs:
  Topic: your_topic_name  Partition: 0  Leader: 1  Replicas: 1  Isr: 1
  Topic: your_topic_name  Partition: 1  Leader: 1  Replicas: 1  Isr: 1
  Topic: your_topic_name  Partition: 2  Leader: 1  Replicas: 1  Isr: 1

----------------------------------------------------------------------------------------------------
-- Update partitions
----------------------------------------------------------------------------------------------------
-- 1. Execute a shell inside the Kafka container
docker exec -it local_kafka /bin/bash

-- 2. Change the Number of Partitions
kafka-topics.sh --alter --topic my-topic --partitions 5 --bootstrap-server localhost:9092

-- Output:
-- To verify the change, you can describe the topic again
kafka-topics.sh --describe --topic my-topic --bootstrap-server localhost:9092

----------------------------------------------------------------------------------------------------