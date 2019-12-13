package net.orekyuu.coinchest.infrastructure.doma.converter

import net.orekyuu.coinchest.account.AccountId
import org.seasar.doma.DomainConverters
import org.seasar.doma.ExternalDomain
import org.seasar.doma.jdbc.domain.DomainConverter

@DomainConverters(
        AccountIdConverter::class
)
sealed class LongDomainConverter<T>(
        private val toBasic: (T) -> Long,
        private val toDomain: (Long) -> T
) : DomainConverter<T, Long> {
    override fun fromDomainToValue(domain: T?): Long? {
        return domain?.let(toBasic)
    }

    override fun fromValueToDomain(value: Long?): T? {
        return value?.let(toDomain)
    }
}

@ExternalDomain
class AccountIdConverter : LongDomainConverter<AccountId>(AccountId::value, ::AccountId)