package net.orekyuu.coinchest.infrastructure.doma.converter

import net.orekyuu.coinchest.point.PointUnit
import org.seasar.doma.DomainConverters
import org.seasar.doma.ExternalDomain
import org.seasar.doma.jdbc.domain.DomainConverter
import java.math.BigDecimal

@DomainConverters(
        PointUnitConverter::class
)
sealed class BigDecimalConverter<T>(
        private val toBasic: (T) -> BigDecimal,
        private val toDomain: (BigDecimal) -> T
) : DomainConverter<T, BigDecimal> {
    override fun fromDomainToValue(domain: T?): BigDecimal? {
        return domain?.let(toBasic)
    }

    override fun fromValueToDomain(value: BigDecimal?): T? {
        return value?.let(toDomain)
    }
}

@ExternalDomain
class PointUnitConverter: BigDecimalConverter<PointUnit>(PointUnit::value, ::PointUnit)