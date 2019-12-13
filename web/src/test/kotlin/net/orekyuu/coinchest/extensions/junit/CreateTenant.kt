package net.orekyuu.coinchest.extensions.junit

import net.orekyuu.coinchest.infrastructure.doma.dao.TenantDao
import net.orekyuu.coinchest.infrastructure.doma.entity.TenantEntity
import net.orekyuu.coinchest.tenant.TenantApiKey
import net.orekyuu.coinchest.tenant.TenantId
import net.orekyuu.coinchest.tenant.TenantName
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CreateTenant(
        val value: String = "test_tenant",
        val displayName: String = "default_tenant_name",
        val apiKey: String = ""
)

class TenantParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.getAnnotationsByType(CreateTenant::class.java)?.firstOrNull() != null
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        val annotation = parameterContext.parameter?.getAnnotationsByType(CreateTenant::class.java)?.first()!!

        val context = SpringExtension.getApplicationContext(extensionContext)
        val tenantDao = context.getBean(TenantDao::class.java)

        val entity = TenantEntity(
                TenantId(getValueIfBlankDefault(annotation.value, UUID.randomUUID().toString())),
                TenantName(annotation.displayName),
                TenantApiKey(getValueIfBlankDefault(annotation.apiKey, UUID.randomUUID().toString())))

        return tenantDao.insert(entity).entity
    }

}