package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.ConsumedPointEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result

@Dao
@ConfigAutowireable
interface ConsumedPointDao {

    @Insert
    fun insert(entity: ConsumedPointEntity): Result<ConsumedPointEntity>
}