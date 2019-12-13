package net.orekyuu.coinchest.point.accountant

import net.orekyuu.coinchest.point.PointAmount


/**
 * ポイント発行量
 */
sealed class IssuedPointAmount<T: IssuedPointAmount<T>>(val value: PointAmount): Comparable<T> {
    override fun compareTo(other: T) = value.compareTo(other.value)

    operator fun plus(other: T) = value + other.value

    operator fun minus(other: T) = value - other.value

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as IssuedPointAmount<*>

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}

/**
 * 無償発行ポイント量
 */
class FreeIssuedPointAmount(value: PointAmount) : IssuedPointAmount<FreeIssuedPointAmount>(value)

/**
 * 有償発行ポイント量
 */
class PaidIssuedPointAmount(value: PointAmount) : IssuedPointAmount<PaidIssuedPointAmount>(value) {
}
