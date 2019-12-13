package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.ProducedPointEntity
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.jdbc.Result

@Dao
@ConfigAutowireable
interface ProducedPointDao {

    @Insert
    fun insert(entity: ProducedPointEntity): Result<ProducedPointEntity>
}