package net.orekyuu.coinchest.infrastructure.doma.converter

import net.orekyuu.coinchest.time.UserTimePeriod
import org.seasar.doma.DomainConverters
import org.seasar.doma.ExternalDomain
import org.seasar.doma.jdbc.domain.DomainConverter
import java.time.LocalDateTime

@DomainConverters(
        UserTimePeriodConverter::class
)
sealed class LocalDateTimeConverter<T>(
        private val toBasic: (T) -> LocalDateTime,
        private val toDomain: (LocalDateTime) -> T
) : DomainConverter<T, LocalDateTime> {
    override fun fromDomainToValue(domain: T?): LocalDateTime? {
        return domain?.let(toBasic)
    }

    override fun fromValueToDomain(value: LocalDateTime?): T? {
        return value?.let(toDomain)
    }
}

@ExternalDomain
class UserTimePeriodConverter: LocalDateTimeConverter<UserTimePeriod>(UserTimePeriod::value, ::UserTimePeriod)