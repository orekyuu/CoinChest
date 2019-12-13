package net.orekyuu.coinchest.infrastructure.doma.entity

import net.orekyuu.coinchest.tenant.TenantApiKey
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.tenant.TenantName
import org.seasar.doma.Entity
import org.seasar.doma.Id
import org.seasar.doma.Table

@Entity(immutable = true)
@Table(name = "tenants")
data class TenantEntity(
        @Id
        val tenantId: TenantId,
        val tenantName: TenantName,
        val apiKey: TenantApiKey
)