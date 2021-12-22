package com.bandi.ravi.kotlin.config

import org.apache.kafka.clients.producer.ProducerConfig
import org.apache.kafka.clients.producer.ProducerRecord
import org.apache.kafka.clients.producer.RecordMetadata
import org.apache.kafka.common.serialization.StringSerializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.core.DefaultKafkaProducerFactory
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.kafka.core.ProducerFactory
import org.springframework.kafka.support.ProducerListener
import java.lang.Exception
import java.util.*

@Configuration
@EnableKafka
class KafkaProducerConfig {

    fun producerConfigs(): MutableMap<String, Any> {
        val props = HashMap<String, Any>()
        props[ProducerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        // If you have custom POJO class to use for value serialization replace StringSerializer with JsonSerializer:class.java
        props[ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG] = StringSerializer::class.java
        // add any other properties required to connect to your Kafka cluster
        return props
    }

    @Bean
    fun producerFactory(): ProducerFactory<String, String> = DefaultKafkaProducerFactory(producerConfigs())

    @Bean
    fun kafkaTemplate(): KafkaTemplate<String, String>? =
        KafkaTemplate(producerFactory()).also {
            it.setProducerListener(
                object : ProducerListener<String?, String?> {
                    override fun onSuccess(
                        producerRecord: ProducerRecord<String?, String?>?,
                        recordMetadata: RecordMetadata?
                    ) {
                        println("ACK from Broker for KafkaProducer: ${producerRecord?.value()} " +
                                "offset: ${recordMetadata?.offset()}")
                    }

                    override fun onError(
                        producerRecord: ProducerRecord<String?, String?>?,
                        recordMetadata: RecordMetadata?,
                        exception: Exception?
                    ) {
                        println("Error while publishing message: ${producerRecord?.value()} " +
                                "offset: ${recordMetadata?.offset()}")
                    }

                }
            )
        }
}