package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.point.UsageCategoryId
import net.orekyuu.coinchest.point.transaction.ChargePointAmount
import net.orekyuu.coinchest.point.transaction.ConsumePointAmount
import net.orekyuu.coinchest.point.transaction.CurrencyType
import net.orekyuu.coinchest.tenant.TenantId
import org.seasar.doma.Entity

@Entity(immutable = true)
data class MonthlyConsumedPointAccountantEntity(
        val tenantId: TenantId,
        val currencyType: CurrencyType,
        val usageCategoryId: UsageCategoryId,
        val consumedAmount: ConsumePointAmount
)

@Entity(immutable = true)
data class MonthlyProducedPointAccountantEntity(
        val tenantId: TenantId,
        val currencyType: CurrencyType,
        val chargePointAmount: ChargePointAmount
)