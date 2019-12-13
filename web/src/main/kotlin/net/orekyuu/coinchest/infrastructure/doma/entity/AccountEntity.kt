package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.account.AccountName
import org.seasar.doma.*

@Entity(immutable = true)
@Table(name = "accounts")
data class AccountEntity(
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        val accountId: AccountId,
        val displayName: AccountName
)