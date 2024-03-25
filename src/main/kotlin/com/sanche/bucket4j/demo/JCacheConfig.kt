package com.sanche.bucket4j.demo

import io.github.bucket4j.distributed.proxy.ProxyManager
import io.github.bucket4j.grid.jcache.JCacheProxyManager
import org.ehcache.config.builders.CacheConfigurationBuilder
import org.ehcache.config.builders.ResourcePoolsBuilder
import org.ehcache.jsr107.Eh107Configuration
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.interceptor.SimpleKey
import org.springframework.cache.jcache.JCacheCacheManager
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
    fun ehCacheManager(): JCacheCacheManager {
        val cacheConfig = CacheConfigurationBuilder
            .newCacheConfigurationBuilder(
                String::class.java,
                String::class.java, ResourcePoolsBuilder.heap(10)
            )
            .build()

        val cacheManager = Caching.getCachingProvider("org.ehcache.jsr107.EhcacheCachingProvider")
            .cacheManager

        val cacheName = "getName"
        cacheManager.destroyCache(cacheName)
        cacheManager.createCache(cacheName, Eh107Configuration.fromEhcacheCacheConfiguration(cacheConfig))

        return JCacheCacheManager(cacheManager)
    }
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