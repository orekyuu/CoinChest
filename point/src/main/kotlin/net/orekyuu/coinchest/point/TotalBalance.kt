package net.orekyuu.coinchest.point

/**
 * 残高
 */
data class TotalBalance(val value: PointAmount)

sealed class Balance(val currencyType: CurrencyType, val amount: PointAmount)

data class BalanceDetails(private val balances: List<Balance>) {
}