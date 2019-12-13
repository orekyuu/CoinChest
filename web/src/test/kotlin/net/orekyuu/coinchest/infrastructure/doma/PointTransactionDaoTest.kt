package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.infrastructure.doma.dao.PointTransactionDao
import net.orekyuu.coinchest.infrastructure.doma.entity.PointTransactionEntity
import net.orekyuu.coinchest.point.transaction.ProduceTransactionId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional


@SpringBootTest
@Transactional
class PointTransactionDaoTest: DaoTestBase() {
    @Autowired
    lateinit var transactionDao: PointTransactionDao

    @Test
    fun insertTest() {
        assertThat(countFunction(jdbcTemplate)).isZero()
        transactionDao.insert(PointTransactionEntity(ProduceTransactionId.newTransaction()))
        assertThat(countFunction(jdbcTemplate)).isOne()
    }

    fun countFunction(jdbcTemplate: JdbcTemplate): Long {
        return jdbcTemplate.queryForObject("select count(*) from point_transactions", Long::class.java) ?: 0
    }
}