package com.sanche.bucket4j.demo

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.BucketConfiguration
import io.github.bucket4j.TokensInheritanceStrategy
import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.Duration
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

inline fun <reified T> T.logger() = LoggerFactory.getLogger(T::class.java)!!

@SpringBootTest
class BucketServiceTest {

    val log = logger()

    @Autowired
    lateinit var bucketService: BucketService

    @Test
    fun bucket() {
        val bucketTest = bucketService.bucket("test")!!
        val count = AtomicLong()
        IntRange(0, 60).forEach {
            if(bucketTest.tryConsume(1)) {
                log.info("{}  {}", count.incrementAndGet(), bucketTest.availableTokens)
            }
            TimeUnit.MILLISECONDS.sleep(100)
        }
        bucketTest.replaceConfiguration(BucketConfiguration.builder()
                .addLimit(Bandwidth.simple(100, Duration.ofSeconds(60)))
                .addLimit(Bandwidth.simple(3, Duration.ofSeconds(1))).build(), TokensInheritanceStrategy.RESET)
        IntRange(0, 60).forEach {
            if(bucketTest.tryConsume(1)) {
                log.info("{}  {}", count.incrementAndGet(), bucketTest.availableTokens)
            }
            TimeUnit.MILLISECONDS.sleep(100)
        }
//        assertTrue( bucketTest.tryConsume(1))
//        assertTrue( bucketTest.tryConsume(1))
//        assertTrue( bucketTest.tryConsume(1))
//        assertTrue( bucketTest.tryConsume(1))
//        assertTrue( bucketTest.tryConsume(1))
//        assertFalse( bucketTest.tryConsume(1))
//        val bucketTest2 = bucketService.bucket("test")!!
//        assertFalse( bucketTest2.tryConsume(1))
//
//        val bucketTest3 = bucketService.bucket("test2")!!
//        assertTrue( bucketTest3.tryConsume(1))
//        assertTrue( bucketTest3.tryConsume(1))
//        assertTrue( bucketTest3.tryConsume(1))
//        assertTrue( bucketTest3.tryConsume(1))
//        assertTrue( bucketTest3.tryConsume(1))
//        assertFalse( bucketTest3.tryConsume(1))
//
//        TimeUnit.MILLISECONDS.sleep(1000)
//        log.info( "{}",bucketTest3.tryConsume(1))
//
//        TimeUnit.MILLISECONDS.sleep(1000)
//        log.info( "{}",bucketTest.tryConsume(1))
//        log.info( "{}",bucketTest.tryConsume(1))
//        log.info( "{}",bucketTest.tryConsume(1))
//        log.info("{}", bucketTest.availableTokens)
//        TimeUnit.MILLISECONDS.sleep(200)
//        log.info("{}", bucketTest.availableTokens)
//        TimeUnit.MILLISECONDS.sleep(200)
//        log.info("{}", bucketTest.availableTokens)
//        TimeUnit.MILLISECONDS.sleep(200)
//        log.info("{}", bucketTest.availableTokens)
//        TimeUnit.MILLISECONDS.sleep(200)
//        log.info("{}", bucketTest.availableTokens)
//        TimeUnit.MILLISECONDS.sleep(200)
//        log.info("{}", bucketTest.availableTokens)
//
//        TimeUnit.MILLISECONDS.sleep(2000)
//        IntRange(0, 40).forEach{
//            log.info( "{}",bucketTest.tryConsume(1))
//            log.info("{}", bucketTest.availableTokens)
//        }

    }
}