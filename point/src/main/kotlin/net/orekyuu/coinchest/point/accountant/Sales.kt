package net.orekyuu.coinchest.point.accountant

import net.orekyuu.coinchest.point.Yen

/**
 * 売上
 *
 * エンドユーザーが有償ポイントを利用したり有効期限が切れたなどの理由で前受金を崩すと売上として計上できる
 */
class Sales(val value: Yen): Comparable<Sales> {
    override fun compareTo(other: Sales) = this.value.compareTo(other.value)

    operator fun plus(other: Sales) = value + other.value

    operator fun minus(other: Sales) = value - other.value
}