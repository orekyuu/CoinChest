package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.extensions.junit.database.ImportJsonData
import net.orekyuu.coinchest.infrastructure.doma.dao.MonthlyAccountantDao
import net.orekyuu.coinchest.infrastructure.doma.entity.MonthlyProducedPointAccountantEntity
import net.orekyuu.coinchest.point.PointAmount
import net.orekyuu.coinchest.point.transaction.ChargePointAmount
import net.orekyuu.coinchest.point.transaction.CurrencyType
import net.orekyuu.coinchest.tenant.TenantId
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Nested
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.time.LocalDate
import java.time.YearMonth

@SpringBootTest
class MonthlyAccountantDaoTest {
    @Autowired
    lateinit var accountantDao: MonthlyAccountantDao

    @Nested
    @SpringBootTest
    inner class ChargeAccountantByYearMonth : DaoTestBase() {

        fun testQuery(targetMonth: YearMonth, aggregateDate: LocalDate): List<MonthlyProducedPointAccountantEntity> {
            val aggregateTime = aggregateDate.atStartOfDay()
            return accountantDao.queryMonthlyChargeAccountantByYearMonth(targetMonth, aggregateTime)
        }

        @Test
        @ImportJsonData("ポイント1件付与")
        fun simpleChargePoint() {
            val result = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2020, 1, 1))

            assertThat(result).hasSize(1).first().extracting {
                assertThat(it.chargePointAmount).isEqualTo(ChargePointAmount(PointAmount(4000)))
                assertThat(it.tenantId).isEqualTo(TenantId("tenant_a"))
                assertThat(it.currencyType).isEqualTo(CurrencyType.PC)
            }
        }

        @Test
        @ImportJsonData("ポイント1件付与")
        fun notFound() {
            val before = testQuery(
                    YearMonth.of(2019, 11),
                    LocalDate.of(2020, 5, 1))

            assertThat(before).isEmpty()

            val after = testQuery(
                    YearMonth.of(2020, 1),
                    LocalDate.of(2020, 5, 1))

            assertThat(after).isEmpty()
        }

        @Test
        @ImportJsonData("ポイント2件付与")
        fun multiChargePoint() {
            val result = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2020, 1, 1))

            assertThat(result).hasSize(1).first().extracting {
                assertThat(it.chargePointAmount).isEqualTo(ChargePointAmount(PointAmount(7000)))
                assertThat(it.tenantId).isEqualTo(TenantId("tenant_a"))
                assertThat(it.currencyType).isEqualTo(CurrencyType.PC)
            }
        }

        @Test
        @ImportJsonData("ポイントをシステム的に削除")
        fun deleteChargePoint() {
            val result = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2020, 1, 1))

            assertThat(result).isEmpty()

            // 15日時点では無効化していないので集計される
            val beforeDelete = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2019, 12, 15))

            assertThat(beforeDelete).hasSize(1)
        }

        @Test
        @ImportJsonData("テナントごとにポイント2件付与")
        fun multiTenant() {
            val result = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2020, 1, 1))
            assertThat(result).hasSize(2)
        }

        @Test
        @ImportJsonData("通貨ごとにポイント2件付与")
        fun multiCurrencyType() {
            val result = testQuery(
                    YearMonth.of(2019, 12),
                    LocalDate.of(2020, 1, 1))
            assertThat(result).hasSize(2)
        }
    }
}