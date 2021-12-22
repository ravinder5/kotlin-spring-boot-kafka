package com.bandi.ravi.kotlin.service

import org.springframework.kafka.annotation.KafkaListener
import org.springframework.stereotype.Service

@Service
class KafkaMessageConsumerService {

    @KafkaListener(
        topics = ["test_topic"],
        // you can also set group id from spring.kafka.consumer.group-id property
        groupId = "kotlin-spring-kafka-app",
        containerFactory = "kafkaListenerContainerFactory"
    )
    fun consumerKafkaMessages(message: String) {
        try {
            println("Consumed message: ${message}")
        } catch (e: Exception) {
            println("Error while consuming message with reason: ${e.message}")
        }
    }
}