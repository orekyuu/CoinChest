package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.point.PointUnit
import net.orekyuu.coinchest.point.transaction.ChargePointAmount
import net.orekyuu.coinchest.point.transaction.CurrencyType
import net.orekyuu.coinchest.point.transaction.ProduceTransactionId
import net.orekyuu.coinchest.tenant.TenantId
import org.seasar.doma.Entity
import org.seasar.doma.Table

@Entity(immutable = true)
@Table(name = "produced_points")
data class ProducedPointEntity(
        val producedTransactionId: ProduceTransactionId,
        val tenantId: TenantId,
        val currencyType: CurrencyType,
        val accountId: AccountId,
        val producedAmount: ChargePointAmount,
        val pointUnit: PointUnit,
        val systemTime: SystemTimeSpanColumn,
        val userTime: UserTimeSpanColumn
)