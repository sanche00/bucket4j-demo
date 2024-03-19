package com.sanche.bucket4j.demo

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.grid.jcache.JCacheProxyManager
import org.springframework.cache.annotation.EnableCaching
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import javax.cache.Cache
import javax.cache.CacheManager
import javax.cache.Caching
import javax.cache.configuration.MutableConfiguration
import javax.cache.expiry.CreatedExpiryPolicy
import javax.cache.expiry.Duration

@Configuration
@EnableCaching
class JCacheConfig {

    @Bean
    fun bucketCacheManager(): CacheManager = Caching.getCachingProvider().cacheManager


    @Bean
    fun ipRateLimitCache(bucketCacheManager: CacheManager): Cache<String, ByteArray> {
        return bucketCacheManager.createCache(
                "rateLimitCache",
                MutableConfiguration<String, ByteArray>().setExpiryPolicyFactory { CreatedExpiryPolicy(Duration.ONE_DAY) })
    }      //

    @Bean
    fun proxyManager(ipRateLimitCache: Cache<String, ByteArray>): ProxyManager<String> {
        return JCacheProxyManager(ipRateLimitCache)
    }
}