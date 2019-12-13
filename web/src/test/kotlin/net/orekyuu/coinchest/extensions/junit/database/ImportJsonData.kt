package net.orekyuu.coinchest.extensions.junit.database

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import org.junit.jupiter.api.extension.BeforeTestExecutionCallback
import org.junit.jupiter.api.extension.ExtensionContext
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.test.context.junit.jupiter.SpringExtension

@Retention(AnnotationRetention.RUNTIME)
@Target(AnnotationTarget.FUNCTION)
annotation class ImportJsonData(val file: String)

class ImportJsonDataParameterResolver : BeforeTestExecutionCallback {
    override fun beforeTestExecution(context: ExtensionContext) {
        val testMethod = context.requiredTestMethod
        val importAnnotation = testMethod.getAnnotation(ImportJsonData::class.java) ?: return
        val applicationContext = SpringExtension.getApplicationContext(context)
        val jdbc = applicationContext.getBean(JdbcTemplate::class.java)

        val tables = readJsonFile(importAnnotation.file)
        tables.forEach {
            it.insertData(jdbc)
        }
    }

    fun readJsonFile(fileName: String): Array<DatabaseTable> {
        return jacksonObjectMapper().readValue(ImportJsonData::class.java.getResourceAsStream("/db/$fileName.json"), Array<DatabaseTable>::class.java) ?: arrayOf()
    }
}

