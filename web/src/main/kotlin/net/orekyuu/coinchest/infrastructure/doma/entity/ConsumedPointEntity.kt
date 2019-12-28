package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.point.UsageCategoryId
import net.orekyuu.coinchest.point.transaction.ConsumePointAmount
import net.orekyuu.coinchest.point.transaction.ConsumeTransactionId
import net.orekyuu.coinchest.tenant.TenantId
import org.seasar.doma.Entity
import org.seasar.doma.Table

@Entity(immutable = true)
@Table(name = "consumed_points")
data class ConsumedPointEntity(
        val consumeTransactionId: ConsumeTransactionId,
        val tenantId: TenantId,
        val usageCategory: UsageCategoryId,
        val accountId: AccountId,
        val consumedAmount: ConsumePointAmount,
        val systemTime: SystemTimeSpanColumn,
        val userTime: UserTimeSpanColumn
)