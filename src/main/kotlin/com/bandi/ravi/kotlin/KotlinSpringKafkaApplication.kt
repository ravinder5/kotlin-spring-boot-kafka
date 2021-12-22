package com.bandi.ravi.kotlin

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class KotlinSpringKafkaApplication

fun main(args: Array<String>) {
	runApplication<KotlinSpringKafkaApplication>(*args)
}
