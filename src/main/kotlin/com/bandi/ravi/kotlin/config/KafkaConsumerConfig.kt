package com.bandi.ravi.kotlin.config

import org.apache.kafka.clients.consumer.ConsumerConfig
import org.apache.kafka.common.serialization.StringDeserializer
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.kafka.annotation.EnableKafka
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory
import org.springframework.kafka.config.KafkaListenerContainerFactory
import org.springframework.kafka.core.ConsumerFactory
import org.springframework.kafka.core.DefaultKafkaConsumerFactory
import org.springframework.kafka.listener.ConcurrentMessageListenerContainer
import org.springframework.kafka.support.serializer.ErrorHandlingDeserializer
import java.util.HashMap

@Configuration
@EnableKafka
class KafkaConsumerConfig {

    fun consumerConfigs(): MutableMap<String, Any> {
        val props = HashMap<String, Any>()
        props[ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG] = "localhost:9092"
        props[ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        // If you have custom POJO class to use for value serialization replace StringDeSerializer with JsonDeSerializer:class.java
        props[ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG] = StringDeserializer::class.java
        // add any other properties required to connect to your Kafka cluster
        return props
    }

    @Bean
    fun consumerFactory(): ConsumerFactory<String?, String?> =
        DefaultKafkaConsumerFactory(
            consumerConfigs(),
            StringDeserializer(),
            // Used ErrorHandlingDeserializer so that we can handle Poison Pill(Term used for corrupt messages that are not deserializable) messages
            ErrorHandlingDeserializer(StringDeserializer())
        )
    // You can also create the above DefaultConsumer as below using Kotlin's also api
//    fun consumerFactory1(): ConsumerFactory<String?, String?> =
//        DefaultKafkaConsumerFactory<String?, String?>(consumerConfigs())
//            .also { it.setValueDeserializer(
//                // Used ErrorHandlingDeserializer so that we can handle Poison Pill(Term used for corrupt messages that are not deserializable) messages
//                ErrorHandlingDeserializer(StringDeserializer())
//            ) }

    @Bean
    fun kafkaListenerContainerFactory(): KafkaListenerContainerFactory<ConcurrentMessageListenerContainer<String?,String?>>? =
        ConcurrentKafkaListenerContainerFactory<String, String>().also { it.consumerFactory = consumerFactory() }

}