package com.bandi.ravi.kotlin.controller

import org.springframework.http.HttpStatus
import org.springframework.http.ResponseEntity
import org.springframework.kafka.core.KafkaTemplate
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class KafkaExampleController(
    val kafkaTemplate: KafkaTemplate<String, String>
) {

    @PostMapping("/")
    fun postMessage(@RequestBody message:String): ResponseEntity<*> {
        kafkaTemplate.send("test_topic", message)
        return ResponseEntity.status(HttpStatus.OK).body(message)
    }
}