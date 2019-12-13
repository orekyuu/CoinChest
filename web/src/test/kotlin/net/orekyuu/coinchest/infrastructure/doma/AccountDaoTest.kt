package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.account.AccountName
import net.orekyuu.coinchest.infrastructure.doma.dao.AccountDao
import net.orekyuu.coinchest.infrastructure.doma.entity.AccountEntity
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class AccountDaoTest: DaoTestBase() {
    @Autowired
    lateinit var accountDao: AccountDao

    @Test
    fun insertTest() {
        assertThat(countFunction(jdbcTemplate)).isZero()

        val result = accountDao.insert(AccountEntity(AccountId(-1), AccountName("test")))

        assertThat(countFunction(jdbcTemplate)).isOne()
        assertThat(result.entity.accountId).isNotEqualTo(AccountId(-1))
    }

    @Test
    fun updateTest() {
        var accountEntity = accountDao.insert(AccountEntity(AccountId(-1), AccountName("test"))).entity

        accountEntity = accountDao.findByIds(listOf(accountEntity.accountId)).first()
        assertThat(accountEntity.displayName).isEqualTo(AccountName("test"))

        accountDao.update(accountEntity.copy(displayName = AccountName("hogera")))

        accountEntity = accountDao.findByIds(listOf(accountEntity.accountId)).first()
        assertThat(accountEntity.displayName).isEqualTo(AccountName("hogera"))

    }

    @Test
    fun findByIdsTest() {
        val account1 = accountDao.insert(AccountEntity(AccountId(-1), AccountName("test1"))).entity
        accountDao.insert(AccountEntity(AccountId(-1), AccountName("test2"))).entity

        assertThat(accountDao.findByIds(listOf(account1.accountId))).hasSize(1).first().extracting {
            assertThat(it.displayName).isEqualTo(AccountName("test1"))
            assertThat(it.accountId).isEqualTo(account1.accountId)
        }
    }

    fun countFunction(jdbcTemplate: JdbcTemplate): Long {
        return jdbcTemplate.queryForObject("select count(*) from accounts", Long::class.java) ?: 0
    }
}