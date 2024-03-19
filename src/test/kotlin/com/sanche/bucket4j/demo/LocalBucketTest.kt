package com.sanche.bucket4j.demo

import io.github.bucket4j.Bandwidth
import io.github.bucket4j.Bucket
import io.github.bucket4j.Refill
import org.junit.jupiter.api.Assertions.assertFalse
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.time.Duration


class LocalBucketTest {

    @Test
    fun testBucket() {
        val refill = Refill.intervally(10, Duration.ofMinutes(1))
        val limit = Bandwidth.classic(10, refill)
        val bucket: Bucket = Bucket.builder()
                .addLimit(limit)
                .build()

        for (i in 1..10) {
            assertTrue(bucket.tryConsume(1))
        }

        assertFalse(bucket.tryConsume(1))
    }
}