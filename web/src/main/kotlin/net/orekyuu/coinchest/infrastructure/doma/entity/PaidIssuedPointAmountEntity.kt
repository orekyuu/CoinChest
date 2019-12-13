package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.point.accountant.PaidIssuedPointAmount
import org.seasar.doma.Entity

@Entity(immutable = true)
data class PaidIssuedPointAmountEntity(
        val issuedPointAmount: PaidIssuedPointAmount
)