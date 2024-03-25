package com.sanche.bucket4j.demo

import org.slf4j.LoggerFactory
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class Bucket4jDemoApplication

fun main(args: Array<String>) {
    runApplication<Bucket4jDemoApplication>(*args)
}

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!
