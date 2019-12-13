package net.orekyuu.coinchest.point

import java.math.BigDecimal

/**
 * 日本円
 */
data class Yen(val value: BigDecimal) : Comparable<Yen> {

    companion object {
        val ZERO = Yen(BigDecimal.ZERO)
    }

    override fun compareTo(other: Yen) = this.value.compareTo(other.value)

    operator fun plus(other: Yen) = this.value + other.value

    operator fun minus(other: Yen) = this.value - other.value
}