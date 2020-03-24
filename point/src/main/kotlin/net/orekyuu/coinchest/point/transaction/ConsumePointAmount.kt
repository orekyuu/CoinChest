package net.orekyuu.coinchest.point.transaction

import net.orekyuu.coinchest.point.PointAmount

/**
 * 消費ポイント量
 */
class ConsumePointAmount(val value: PointAmount) {
    companion object {
        val ZERO = ConsumePointAmount(PointAmount.ZERO)
    }

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ConsumePointAmount

        if (value != other.value) return false

        return true
    }

    override fun hashCode(): Int {
        return value.hashCode()
    }


}