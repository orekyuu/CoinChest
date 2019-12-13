package net.orekyuu.coinchest.point

import java.math.BigDecimal

/**
 * ポイント単価
 */
data class PointUnit(val value: BigDecimal): Comparable<PointUnit> {

    override fun compareTo(other: PointUnit) = this.value.compareTo(other.value)
}