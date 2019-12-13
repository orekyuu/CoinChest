package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.PointTransactionEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result

@Dao
@ConfigAutowireable
interface PointTransactionDao {

    @Insert
    fun insert(entity: PointTransactionEntity): Result<PointTransactionEntity>
}