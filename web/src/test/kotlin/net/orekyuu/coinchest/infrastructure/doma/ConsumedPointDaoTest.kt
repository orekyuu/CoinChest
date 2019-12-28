package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.extensions.junit.CreateAccount
import net.orekyuu.coinchest.extensions.junit.CreateTenant
import net.orekyuu.coinchest.infrastructure.doma.dao.ConsumedPointDao
import net.orekyuu.coinchest.infrastructure.doma.dao.PointTransactionDao
import net.orekyuu.coinchest.infrastructure.doma.entity.*
import net.orekyuu.coinchest.point.PointAmount
import net.orekyuu.coinchest.point.UsageCategoryId
import net.orekyuu.coinchest.point.transaction.ConsumePointAmount
import net.orekyuu.coinchest.point.transaction.ConsumeTransactionId
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.time.SystemTimeSpan
import net.orekyuu.coinchest.time.UserTimeSpan
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional
import java.time.Clock


@SpringBootTest
@Transactional
class ConsumedPointDaoTest: DaoTestBase() {
    @Autowired
    lateinit var consumedPointDao: ConsumedPointDao
    @Autowired
    lateinit var transactionDao: PointTransactionDao

    @Test
    fun insertTest(
            @CreateAccount accountEntity: AccountEntity,
            @CreateTenant("tenant") tenantEntity: TenantEntity) {
        val transactionId = ConsumeTransactionId.newTransaction()
        transactionDao.insert(PointTransactionEntity(transactionId))

        assertThat(countFunction(jdbcTemplate)).isZero()

        val clock = Clock.systemDefaultZone()
        consumedPointDao.insert(ConsumedPointEntity(
                transactionId,
                TenantId("tenant"),
                UsageCategoryId("service"),
                accountEntity.accountId,
                ConsumePointAmount(PointAmount(1000)),
                SystemTimeSpanColumn(SystemTimeSpan.nowToInfinity(clock)),
                UserTimeSpanColumn(UserTimeSpan.nowToInfinity(clock))
        ))
        assertThat(countFunction(jdbcTemplate)).isOne()
    }

    fun countFunction(jdbcTemplate: JdbcTemplate): Long {
        return jdbcTemplate.queryForObject("select count(*) from consumed_points", Long::class.java) ?: 0
    }
}