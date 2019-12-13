package net.orekyuu.coinchest.infrastructure.doma.converter

import net.orekyuu.coinchest.account.AccountName
import net.orekyuu.coinchest.point.UsageCategoryId
import net.orekyuu.coinchest.point.transaction.ConsumeTransactionId
import net.orekyuu.coinchest.point.transaction.ProduceTransactionId
import net.orekyuu.coinchest.point.transaction.TransactionId
import net.orekyuu.coinchest.tenant.TenantApiKey
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.tenant.TenantName
import org.seasar.doma.DomainConverters
import org.seasar.doma.ExternalDomain
import org.seasar.doma.jdbc.domain.DomainConverter

@DomainConverters(
        AccountNameConverter::class,
        TenantIdConverter::class,
        TenantNameConverter::class,
        TenantApiKeyConverter::class,
        TransactionIdConverter::class,
        ProduceTransactionIdConverter::class,
        ConsumedTransactionIdConverter::class,
        UsageCategoryIdConverter::class
)
sealed class StringDomainConverter<T>(
        private val toBasic: (T) -> String,
        private val toDomain: (String) -> T
) : DomainConverter<T, String> {
    override fun fromDomainToValue(domain: T?): String? {
        return domain?.let(toBasic)
    }

    override fun fromValueToDomain(value: String?): T? {
        return value?.let(toDomain)
    }
}

@ExternalDomain
class AccountNameConverter : StringDomainConverter<AccountName>(AccountName::value, ::AccountName)
@ExternalDomain
class TenantIdConverter : StringDomainConverter<TenantId>(TenantId::value, ::TenantId)
@ExternalDomain
class TenantNameConverter : StringDomainConverter<TenantName>(TenantName::value, ::TenantName)
@ExternalDomain
class TenantApiKeyConverter : StringDomainConverter<TenantApiKey>(TenantApiKey::value, ::TenantApiKey)
@ExternalDomain
class TransactionIdConverter : StringDomainConverter<TransactionId>(TransactionId::value, ::TransactionId)
@ExternalDomain
class ProduceTransactionIdConverter : StringDomainConverter<ProduceTransactionId>(ProduceTransactionId::value, ::ProduceTransactionId)
@ExternalDomain
class ConsumedTransactionIdConverter : StringDomainConverter<ConsumeTransactionId>(ConsumeTransactionId::value, ::ConsumeTransactionId)
@ExternalDomain
class UsageCategoryIdConverter : StringDomainConverter<UsageCategoryId>(UsageCategoryId::value, ::UsageCategoryId)
