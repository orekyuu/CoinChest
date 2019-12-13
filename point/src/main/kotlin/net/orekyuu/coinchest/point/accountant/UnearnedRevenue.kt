package net.orekyuu.coinchest.point.accountant

import net.orekyuu.coinchest.point.Yen


/**
 * 前受金
 *
 * エンドユーザーがポイント購入時に支払った現金。この時点では売上にならない
 */
class UnearnedRevenue(val value: Yen): Comparable<UnearnedRevenue> {
    override fun compareTo(other: UnearnedRevenue) = this.value.compareTo(other.value)

    operator fun plus(other: UnearnedRevenue) = value + other.value

    operator fun minus(other: UnearnedRevenue) = value - other.value
}