package net.orekyuu.coinchest.extensions.junit.database

import org.springframework.jdbc.core.BatchPreparedStatementSetter
import org.springframework.jdbc.core.JdbcTemplate
import java.sql.PreparedStatement

data class DatabaseTable(val table: String, val records: List<Map<String, Any>>) {

    fun insertData(jdbcTemplate: JdbcTemplate) {
        val first = records.firstOrNull() ?: return
        val columns = first.keys
        val joinedColumn = columns.joinToString(", ")
        val variables = columns.map { "?" }.joinToString(", ")

        jdbcTemplate.batchUpdate(
                "insert into $table($joinedColumn) values ($variables)",
                object : BatchPreparedStatementSetter {
                    override fun setValues(ps: PreparedStatement, i: Int) {
                        val record = records[i]

                        columns.forEachIndexed { index, key ->
                            // parameterIndexは1スタートなので注意 ;(
                            ps.setObject(index + 1, record[key])
                        }
                        println(ps.toString())
                    }

                    override fun getBatchSize() = records.size
                })
    }
}