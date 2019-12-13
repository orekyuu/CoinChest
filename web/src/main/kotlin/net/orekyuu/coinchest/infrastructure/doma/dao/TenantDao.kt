package net.orekyuu.coinchest.infrastructure.doma.dao

import net.orekyuu.coinchest.infrastructure.doma.entity.TenantEntity
import net.orekyuu.coinchest.tenant.TenantId
import org.seasar.doma.Dao
import org.seasar.doma.Insert
import org.seasar.doma.Select
import org.seasar.doma.Update
import org.seasar.doma.boot.ConfigAutowireable
import org.seasar.doma.experimental.Sql
import org.seasar.doma.jdbc.Result

@Dao
@ConfigAutowireable
interface TenantDao {
    @Insert
    fun insert(entity: TenantEntity): Result<TenantEntity>

    @Update
    fun update(entity: TenantEntity): Result<TenantEntity>

    @Select
    @Sql("""
        select /*%expand*/* from tenants where tenant_id in /*ids*/('tenant_a', 'tenant_b')
    """)
    fun findByIds(ids: Iterable<TenantId>): List<TenantEntity>
}