package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.MonthlyProducedPointAccountantEntity
import net.orekyuu.coinchest.infrastructure.doma.entity.MonthlyUsedPointAccountantEntity
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

    @Select
    @Sql("""
        select 
          used_points.tenant_id,
          currency_type,
          usage_category,
          sum(amount) as consumed_amount
        from used_points
        left join consumed_points consumed on used_points.consumed_transaction_id = consumed.consume_transaction_id
        where used_points.system_in <= /*aggregateTime*/'' and used_points.system_out > /*aggregateTime*/''
        and DATE_FORMAT(used_points.user_in, '%Y%m') = DATE_FORMAT(/*month.atEndOfMonth()*/'', '%Y%m')
        group by used_points.tenant_id, used_points.currency_type, consumed.usage_category
    """)
    fun queryMonthlyUseAccountantByYearMonth(month: YearMonth, aggregateTime: LocalDateTime): List<MonthlyUsedPointAccountantEntity>
}