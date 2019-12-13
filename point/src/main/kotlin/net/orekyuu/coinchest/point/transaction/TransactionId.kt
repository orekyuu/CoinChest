package net.orekyuu.coinchest.point.transaction

import java.util.*

/**
 * 取引ID
 */
open class TransactionId(val value: String)

/**
 * ポイント増加取引ID
 */
class ProduceTransactionId(value: String): TransactionId(value) {
    companion object {
        fun newTransaction(): ProduceTransactionId {
            return ProduceTransactionId("Produce-" + UUID.randomUUID().toString())
        }
    }
}

/**
 * ポイント減少取引ID
 */
class ConsumeTransactionId(value: String): TransactionId(value) {
    companion object {
        fun newTransaction(): ConsumeTransactionId {
            return ConsumeTransactionId("Consume-" + UUID.randomUUID().toString())
        }
    }
}