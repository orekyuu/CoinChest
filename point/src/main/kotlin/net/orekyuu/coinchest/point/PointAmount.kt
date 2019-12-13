package net.orekyuu.coinchest.point

import java.math.BigInteger

/**
 * ポイント量
 */
class PointAmount(val value: BigInteger): Comparable<PointAmount> {
    constructor(value: Long) : this(BigInteger.valueOf(value))

    companion object {
        val ZERO = PointAmount(BigInteger.valueOf(0))
    }

    override fun compareTo(other: PointAmount) = this.value.compareTo(other.value)

    operator fun plus(other: PointAmount) = this.value + other.value

    operator fun minus(other: PointAmount) = this.value - other.value

    // ポイント量にポイント単価をかけると日本円になる
    operator fun times(unit: PointUnit) = Yen(this.value.toBigDecimal() * unit.value)

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PointAmount

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}