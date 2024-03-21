package com.sanche.bucket4j.demo

import org.junit.jupiter.api.Test

class Calculate {


    data class Data(var sec: Int, var cnt: Int)

    @Test
    fun test1() {
        var data1 = Data(60, 40)
        val rate = 80
        val d = data1.cnt.toDouble() * rate.toDouble() / 100

        val x = IntRange(1, 10).map {
            var t1 = data1.sec * it
            var t2 = data1.cnt * it
            val d = t2.toDouble() * rate.toDouble() / 100
            if (d.mod(1.0) == 0.0) {
                Data(t1, d.toInt())
            } else {
                null
            }
        }.first { it != null }
        println(x)

    }
}