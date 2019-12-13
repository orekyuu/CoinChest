package net.orekyuu.coinchest

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

fun main(args: Array<String>) {
    SpringApplication.run(CoinChestApplication::class.java, *args)
}

@SpringBootApplication
class CoinChestApplication