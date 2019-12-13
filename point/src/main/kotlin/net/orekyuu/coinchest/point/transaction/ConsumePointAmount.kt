package net.orekyuu.coinchest.point.transaction

import net.orekyuu.coinchest.point.PointAmount

/**
 * 消費ポイント量
 */
class ConsumePointAmount(val value: PointAmount) {
    companion object {
        val ZERO = ConsumePointAmount(PointAmount.ZERO)
    }
}