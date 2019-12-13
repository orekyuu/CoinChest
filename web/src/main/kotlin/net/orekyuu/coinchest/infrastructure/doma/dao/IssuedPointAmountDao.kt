package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.PaidIssuedPointAmountEntity
import net.orekyuu.coinchest.time.UserTimePeriod
import org.seasar.doma.Dao
import org.seasar.doma.Select
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.experimental.Sql

@Dao
@ConfigAutowireable
interface IssuedPointAmountDao {

    /**
     * ユーザー時間ベースで見た有料ポイントの発行額を通過タイプ、テナントタイプごとに集計したもの
     */
    @Select
    @Sql("""
        select sum(produced_amount) as issued_point_amount
        from produced_points
        where system_in <= /*userTime*/'' and system_out > /*userTime*/''
        and user_in <= /*userTime*/''
        group by tenant_id, currency_type
    """)
    fun findPaidIssuedPointAmount(userTime: UserTimePeriod): List<PaidIssuedPointAmountEntity>

}