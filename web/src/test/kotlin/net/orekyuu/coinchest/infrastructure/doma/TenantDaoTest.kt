package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.infrastructure.doma.dao.TenantDao
import net.orekyuu.coinchest.infrastructure.doma.entity.TenantEntity
import net.orekyuu.coinchest.tenant.TenantApiKey
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.tenant.TenantName
import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional

@SpringBootTest
@Transactional
class TenantDaoTest: DaoTestBase() {
    @Autowired
    lateinit var tenantDao: TenantDao

    @Test
    fun insertTest() {
        assertThat(countFunction(jdbcTemplate)).isZero()

        val result = tenantDao.insert(TenantEntity(TenantId("tenant_a"), TenantName("tenant_a"), TenantApiKey("api_key")))

        assertThat(countFunction(jdbcTemplate)).isOne()
        assertThat(result.entity.tenantId).isEqualTo(TenantId("tenant_a"))
    }

    @Test
    fun updateTest() {
        var tenantEntity = tenantDao.insert(TenantEntity(TenantId("tenant_a"), TenantName("tenant_a"), TenantApiKey("api_key"))).entity

        tenantEntity = tenantDao.findByIds(listOf(tenantEntity.tenantId)).first()
        assertThat(tenantEntity.tenantName).isEqualTo(TenantName("tenant_a"))

        tenantDao.update(tenantEntity.copy(tenantName = TenantName("hogera")))

        tenantEntity = tenantDao.findByIds(listOf(tenantEntity.tenantId)).first()
        assertThat(tenantEntity.tenantName).isEqualTo(TenantName("hogera"))

    }

    @Test
    fun findByIdsTest() {
        val tenant1 = tenantDao.insert(TenantEntity(TenantId("tenant_a"), TenantName("tenant_a"), TenantApiKey("api_key"))).entity
        tenantDao.insert(TenantEntity(TenantId("tenant_b"), TenantName("tenant_b"), TenantApiKey("api_key_b"))).entity

        assertThat(tenantDao.findByIds(listOf(tenant1.tenantId))).hasSize(1).first().extracting {
            assertThat(it.tenantName).isEqualTo(TenantName("tenant_a"))
            assertThat(it.tenantId).isEqualTo(tenant1.tenantId)
        }
    }

    fun countFunction(jdbcTemplate: JdbcTemplate): Long {
        return jdbcTemplate.queryForObject("select count(*) from tenants", Long::class.java) ?: 0
    }
}