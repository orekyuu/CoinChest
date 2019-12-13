package net.orekyuu.coinchest.point

data class CurrencyId(val value: String)

data class CurrencyName(val value: String)

/**
 * 通貨タイプ
 */
class CurrencyType(val id: CurrencyId, val name: CurrencyName, val isFree: Boolean) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as CurrencyType

        if (id != other.id) return false

        return true
    }

    override fun hashCode(): Int {
        return id.hashCode()
    }
}