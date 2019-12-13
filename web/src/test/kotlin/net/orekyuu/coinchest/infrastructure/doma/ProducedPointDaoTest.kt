package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.extensions.junit.CreateAccount
import net.orekyuu.coinchest.extensions.junit.CreateTenant
import net.orekyuu.coinchest.infrastructure.doma.dao.PointTransactionDao
import net.orekyuu.coinchest.infrastructure.doma.dao.ProducedPointDao
import net.orekyuu.coinchest.infrastructure.doma.entity.*
import net.orekyuu.coinchest.point.PointAmount
import net.orekyuu.coinchest.point.PointUnit
import net.orekyuu.coinchest.point.transaction.ChargePointAmount
import net.orekyuu.coinchest.point.transaction.CurrencyType
import net.orekyuu.coinchest.point.transaction.ProduceTransactionId
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.time.SystemTimeSpan
import net.orekyuu.coinchest.time.UserTimeSpan
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional
import java.math.BigDecimal
import java.time.Clock


@SpringBootTest
@Transactional
class ProducedPointDaoTest: DaoTestBase() {
    @Autowired
    lateinit var producedPointDao: ProducedPointDao
    @Autowired
    lateinit var transactionDao: PointTransactionDao

    @Test
    fun insertTest(
            @CreateAccount accountEntity: AccountEntity,
            @CreateTenant("tenant") tenantEntity: TenantEntity) {
        val transactionId = ProduceTransactionId.newTransaction()
        transactionDao.insert(PointTransactionEntity(transactionId))

        assertThat(countFunction(jdbcTemplate)).isZero()

        val clock = Clock.systemDefaultZone()
        producedPointDao.insert(ProducedPointEntity(
                transactionId,
                TenantId("tenant"),
                CurrencyType.PC,
                accountEntity.accountId,
                ChargePointAmount(PointAmount(1000)),
                PointUnit(BigDecimal.valueOf(1.2)),
                SystemTimeSpanColumn(SystemTimeSpan.nowToInfinity(clock)),
                UserTimeSpanColumn(UserTimeSpan.nowToInfinity(clock))
        ))
        assertThat(countFunction(jdbcTemplate)).isOne()
    }

    fun countFunction(jdbcTemplate: JdbcTemplate): Long {
        return jdbcTemplate.queryForObject("select count(*) from produced_points", Long::class.java) ?: 0
    }
}