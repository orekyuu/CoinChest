package net.orekyuu.coinchest.point.transaction

import net.orekyuu.coinchest.point.PointAmount

/**
 * チャージされたポイント量
 */
class ChargePointAmount(val value: PointAmount) {

    companion object {
        val ZERO = ChargePointAmount(PointAmount.ZERO)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ChargePointAmount

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}