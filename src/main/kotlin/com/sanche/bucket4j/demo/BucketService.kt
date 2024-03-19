package com.sanche.bucket4j.demo

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BandwidthBuilder
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.ConfigurationBuilder
import io.github.bucket4j.Refill
import io.github.bucket4j.distributed.BucketProxy
import io.github.bucket4j.distributed.proxy.ProxyManager
import org.springframework.stereotype.Service
import java.time.Duration
import javax.cache.Cache

@Service
class BucketService(val proxyManager: ProxyManager<String>) {

    fun bucket(name: String): BucketProxy? {
        return proxyManager.builder().build(name) {
            ofBuilder(BucketConfiguration.builder())
        }
    }

    fun ofBuilder(builder: ConfigurationBuilder): BucketConfiguration {
//        return builder.addLimit(Bandwidth.simple(40, Duration.ofSeconds(60)))
//                .addLimit(Bandwidth.simple(2, Duration.ofSeconds(1))).build()

        return builder
                .addLimit { Bandwidth.builder().capacity(40L).refillGreedy(40L, Duration.ofMinutes(1)) }
                .addLimit { Bandwidth.builder().capacity(2L).refillGreedy(2L, Duration.ofMillis(1000)) }.build()
    }
}