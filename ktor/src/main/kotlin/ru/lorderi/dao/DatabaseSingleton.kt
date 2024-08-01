package ru.lorderi.dao

import kotlinx.coroutines.Dispatchers
import org.jetbrains.exposed.sql.Database
import org.jetbrains.exposed.sql.SchemaUtils
import org.jetbrains.exposed.sql.transactions.experimental.newSuspendedTransaction
import org.jetbrains.exposed.sql.transactions.transaction
import ru.lorderi.dao.table.*

object DatabaseSingleton {
    fun init() {
        val driverClassName = "org.h2.Driver"
        val jdbcURL = "jdbc:h2:file:./build/db1"
        val database = Database.connect(jdbcURL, driverClassName)
        transaction(database) {
//            addLogger(StdOutSqlLogger)
            SchemaUtils.create(DBCompanies, DBVacancies, DBResumes, DBEducation, DBJobExperience, DBTags)
        }
    }

    suspend fun <T> dbQuery(block: suspend () -> T): T =
        newSuspendedTransaction(Dispatchers.IO) { block() }
}
