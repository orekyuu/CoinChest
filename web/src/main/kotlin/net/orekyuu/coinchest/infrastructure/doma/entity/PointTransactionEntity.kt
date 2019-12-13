package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.point.transaction.TransactionId
import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.Table

@Entity(immutable = true)
@Table(name = "point_transactions")
data class PointTransactionEntity(
        @Id
        val transactionId: TransactionId
)