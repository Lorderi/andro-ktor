package ru.lorderi.dao.table

import org.jetbrains.exposed.sql.Table

object DBVacancies : Table() {
    val id = long("id").autoIncrement()
    val companyId = reference("company_id", DBCompanies.id)
    val company = varchar("company", 50)
    val profession = varchar("profession", 50)
    val level = varchar("level", 50)
    var salary = integer("salary")

    override val primaryKey = PrimaryKey(id)
}
