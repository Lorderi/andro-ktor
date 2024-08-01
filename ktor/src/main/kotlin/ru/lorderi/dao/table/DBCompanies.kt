package ru.lorderi.dao.table

import org.jetbrains.exposed.sql.Table

object DBCompanies : Table() {
    val id = long("id").autoIncrement()
    val name = varchar("name", 50)
    val activity = varchar("activity", 50)
    val contacts = varchar("contacts", 50)

    override val primaryKey = PrimaryKey(id)
}
