package net.orekyuu.coinchest.infrastructure.doma.converter

import net.orekyuu.coinchest.point.PointAmount
import net.orekyuu.coinchest.point.accountant.PaidIssuedPointAmount
import net.orekyuu.coinchest.point.transaction.ChargePointAmount
import net.orekyuu.coinchest.point.transaction.ConsumePointAmount
import org.seasar.doma.DomainConverters
import org.seasar.doma.ExternalDomain
import org.seasar.doma.jdbc.domain.DomainConverter
import java.math.BigInteger

@DomainConverters(
        ChargePointAmountConverter::class,
        ConsumePointAmountConverter::class,
        PaidIssuedPointAmountConverter::class
)
sealed class BigIntegerConverter<T>(
        private val toBasic: (T) -> BigInteger,
        private val toDomain: (BigInteger) -> T
) : DomainConverter<T, BigInteger> {
    override fun fromDomainToValue(domain: T?): BigInteger? {
        return domain?.let(toBasic)
    }

    override fun fromValueToDomain(value: BigInteger?): T? {
        return value?.let(toDomain)
    }
}

@ExternalDomain
class ChargePointAmountConverter : BigIntegerConverter<ChargePointAmount>(
        { it.value.value },
        { ChargePointAmount(PointAmount(it)) }
)

@ExternalDomain
class ConsumePointAmountConverter : BigIntegerConverter<ConsumePointAmount>(
        { it.value.value },
        { ConsumePointAmount(PointAmount(it)) }
)

@ExternalDomain
class PaidIssuedPointAmountConverter : BigIntegerConverter<PaidIssuedPointAmount>(
        { it.value.value },
        { PaidIssuedPointAmount(PointAmount(it)) }
)