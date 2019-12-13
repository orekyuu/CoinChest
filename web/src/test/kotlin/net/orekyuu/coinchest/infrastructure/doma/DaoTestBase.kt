package net.orekyuu.coinchest.infrastructure.doma

import net.orekyuu.coinchest.extensions.junit.AccountParameterResolver
import net.orekyuu.coinchest.extensions.junit.TenantParameterResolver
import net.orekyuu.coinchest.extensions.junit.database.ImportJsonDataParameterResolver
import org.junit.jupiter.api.extension.ExtendWith
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.transaction.annotation.Transactional


@Transactional
@ExtendWith(TenantParameterResolver::class, AccountParameterResolver::class, ImportJsonDataParameterResolver::class)
class DaoTestBase {
    @Autowired
    lateinit var jdbcTemplate: JdbcTemplate
}