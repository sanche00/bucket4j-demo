package com.sanche.bucket4j.demo

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class CacheServiceTest {

    val log = logger()
    @Autowired
    lateinit var cacheService: CacheService

    @Test
    fun getName() {
        log.info(cacheService.getName("test"))
        log.info(cacheService.getName("test"))
        log.info(cacheService.getName("test1"))
        log.info(cacheService.getName("test1"))
    }
}