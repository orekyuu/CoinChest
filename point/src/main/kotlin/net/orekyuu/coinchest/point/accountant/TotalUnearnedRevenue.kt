package net.orekyuu.coinchest.point.accountant

import net.orekyuu.coinchest.point.Yen

/**
 * 累計前受金
 */
class TotalUnearnedRevenue(val value: Yen) {

    /**
     * 累計前受金 / 有償ポイント累計発行数 = ポイントレート
     */
    operator fun div(other: PaidIssuedPointAmount) = PointRate(value.value / other.value.value.toBigDecimal())
}