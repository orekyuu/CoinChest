package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.infrastructure.doma.entity.AccountEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.Update
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.experimental.Sql
import org.seasar.doma.jdbc.Result

@Dao
@ConfigAutowireable
interface AccountDao {

    @Insert
    fun insert(entity: AccountEntity): Result<AccountEntity>

    @Update
    fun update(entity: AccountEntity): Result<AccountEntity>

    @Select
    @Sql("""
        select /*%expand*/* from accounts where account_id in /*ids*/(1, 2)
    """)
    fun findByIds(ids: Iterable<AccountId>): List<AccountEntity>
}