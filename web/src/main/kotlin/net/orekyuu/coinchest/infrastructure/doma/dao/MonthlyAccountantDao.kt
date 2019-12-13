package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.MonthlyProducedPointAccountantEntity
import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.experimental.Sql
import java.time.LocalDateTime
import java.time.YearMonth

@Dao
@ConfigAutowireable
interface MonthlyAccountantDao {

    @Select
    @Sql("""
        select 
          tenant_id,
          currency_type,
          sum(produced_amount) as charge_point_amount
        from produced_points
        where system_in <= /*aggregateTime*/'' and system_out > /*aggregateTime*/''
        and DATE_FORMAT(user_in, '%Y%m') = DATE_FORMAT(/*month.atEndOfMonth()*/'', '%Y%m')
        group by tenant_id, currency_type
    """)
    fun queryMonthlyChargeAccountantByYearMonth(month: YearMonth, aggregateTime: LocalDateTime): List<MonthlyProducedPointAccountantEntity>
}