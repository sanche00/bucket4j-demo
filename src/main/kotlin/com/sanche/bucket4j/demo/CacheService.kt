package com.sanche.bucket4j.demo

import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service

@Service
class CacheService {

    val log = logger()
    @Cacheable(cacheNames = ["getName"], key = "#name")
    fun getName(name : String): String {
        log.info(name)
        return name
    }

}