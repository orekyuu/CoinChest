package net.orekyuu.coinchest.extensions.junit

import net.orekyuu.coinchest.account.AccountId
import net.orekyuu.coinchest.account.AccountName
import net.orekyuu.coinchest.infrastructure.doma.dao.AccountDao
import net.orekyuu.coinchest.infrastructure.doma.entity.AccountEntity
import org.junit.jupiter.api.extension.ExtensionContext
import org.junit.jupiter.api.extension.ParameterContext
import org.junit.jupiter.api.extension.ParameterResolver
import org.springframework.test.context.junit.jupiter.SpringExtension
import java.util.*

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.VALUE_PARAMETER)
annotation class CreateAccount(
        val displayName: String = "user_name"
)

class AccountParameterResolver : ParameterResolver {
    override fun supportsParameter(parameterContext: ParameterContext?, extensionContext: ExtensionContext?): Boolean {
        return parameterContext?.parameter?.getAnnotationsByType(CreateAccount::class.java)?.firstOrNull() != null
    }

    override fun resolveParameter(parameterContext: ParameterContext, extensionContext: ExtensionContext): Any {
        val annotation = parameterContext.parameter?.getAnnotationsByType(CreateAccount::class.java)?.first()!!

        val context = SpringExtension.getApplicationContext(extensionContext)
        val dao = context.getBean(AccountDao::class.java)

        val entity = AccountEntity(AccountId(-1), AccountName(getValueIfBlankDefault(annotation.displayName, UUID.randomUUID().toString())))

        return dao.insert(entity).entity
    }

}