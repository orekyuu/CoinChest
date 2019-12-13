package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.extensions.junit.database.ImportJsonData
import net.orekyuu.coinchest.infrastructure.doma.dao.IssuedPointAmountDao
import net.orekyuu.coinchest.infrastructure.doma.entity.PaidIssuedPointAmountEntity
import net.orekyuu.coinchest.point.PointAmount
import net.orekyuu.coinchest.point.accountant.PaidIssuedPointAmount
import net.orekyuu.coinchest.time.UserTimePeriod
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.DisplayName
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.transaction.annotation.Transactional
import java.time.LocalDate
import java.time.LocalDateTime


@SpringBootTest
@Transactional
class IssuedPointAmountDaoTest: DaoTestBase() {
    @Autowired
    lateinit var issuedPointAmountDao: IssuedPointAmountDao

    @Test
    @ImportJsonData("ポイント1件付与")
    fun simpleIssuedPoint() {
        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 31, 23, 59, 59)))
        assertThat(result).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(4000)))
        }
    }

    @Test
    @ImportJsonData("ポイント1件付与")
    fun pointNotFound() {
        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 11, 1, 0, 0, 0)))
        assertThat(result).isEmpty()
    }

    @Test
    @ImportJsonData("ポイント2件付与")
    fun multiIssuedPoint() {
        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 31, 23, 59, 59)))
        assertThat(result).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(7000)))
        }
    }

    @Test
    @ImportJsonData("テナントごとにポイント2件付与")
    fun multiTenantIssuedPoint() {
        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 31, 23, 59, 59)))
        assertThat(result.map(PaidIssuedPointAmountEntity::issuedPointAmount)).hasSize(2).containsExactly(
                PaidIssuedPointAmount(PointAmount(7000)),
                PaidIssuedPointAmount(PointAmount(500))
        )
    }

    @Test
    @ImportJsonData("通貨ごとにポイント2件付与")
    fun multiCurrencyTypeIssuedPoint() {
        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 31, 23, 59, 59)))
        assertThat(result.map(PaidIssuedPointAmountEntity::issuedPointAmount)).hasSize(2).containsExactly(
                PaidIssuedPointAmount(PointAmount(3000)),
                PaidIssuedPointAmount(PointAmount(200))
        )
    }

    @Test
    @ImportJsonData("ポイント付与額修正")
    @DisplayName("ポイント付与額が修正される場合、以前の記録を残しつつ集計できる")
    fun fixIssuedPoint() {
        val beforeFix = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 13, 23, 59, 59)))
        assertThat(beforeFix).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(4000)))
        }

        val result = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDateTime.of(2019, 12, 31, 23, 59, 59)))
        assertThat(result).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(3000)))
        }
    }

    @Test
    @ImportJsonData("ポイント付与予約と有効期限が設定されている")
    @DisplayName("ユーザーからみたポイント付与予定日時から集計対象となる")
    fun scheduledIssuedPoint() {
        val inserted = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2019, 12, 13).atStartOfDay()))
        assertThat(inserted).isEmpty()

        val scheduled = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2019, 12, 15).atStartOfDay()))
        assertThat(scheduled).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(1000)))
        }
    }

    @Test
    @ImportJsonData("ポイント付与予約と有効期限が設定されている")
    @DisplayName("有効期限切れになっても集計対象になる")
    fun expiredIssuedPoint() {
        val beforeExpire = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2020, 1, 9).atStartOfDay()))
        assertThat(beforeExpire).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(1000)))
        }

        // Note: 発行額を見たいので、有効期限が切れたとしても集計対象としてのこっている
        val expired = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2020, 1, 10).atStartOfDay()))
        assertThat(expired).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(1000)))
        }
    }

    @Test
    @ImportJsonData("ポイントをシステム的に削除")
    @DisplayName("システム的にポイント付与を無効化した場合、付与の履歴を残しつつ集計上の無効化をすることができる")
    fun deleteIssuedPoint() {
        val beforeInsert = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2019, 12, 10).atStartOfDay()))
        assertThat(beforeInsert).isEmpty()

        val inserted = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2019, 12, 13).atStartOfDay()))
        assertThat(inserted).hasSize(1).first().extracting {
            assertThat(it.issuedPointAmount).isEqualByComparingTo(PaidIssuedPointAmount(PointAmount(2000)))
        }

        // Note: システム的に無効にしたのでポイント発行額からも消える
        val deleted = issuedPointAmountDao.findPaidIssuedPointAmount(UserTimePeriod(LocalDate.of(2019, 12, 21).atStartOfDay()))
        assertThat(deleted).isEmpty()
    }
}