package ru.lorderi.dao.table

import org.jetbrains.exposed.sql.Table

object DBEducation : Table() {
    val id = long("id").autoIncrement()
    val resumeId = reference("resume_id", DBResumes.id)
    val type = varchar("type", 50)
    val yearStart = varchar("year_start", 50)
    val yearEnd = varchar("year_end", 50)
    val description = varchar("description", 50)

    override val primaryKey = PrimaryKey(id)
}

object DBJobExperience : Table() {
    val id = long("id").autoIncrement()
    val resumeId = reference("resume_id", DBResumes.id)
    val dateStart = varchar("date_start", 50)
    val dateEnd = varchar("date_end", 50)
    val companyName = varchar("company_name", 50)
    val description = varchar("description", 50)

    override val primaryKey = PrimaryKey(id)
}

object DBResumes : Table() {
    val id = long("id").autoIncrement()

    // Candidate info
    val name = varchar("name", 50)
    val profession = varchar("profession", 50)
    val sex = varchar("sex", 50)
    val birthDate = varchar("birth_date", 50)

    // Contacts
    val phone = varchar("phone", 50)
    val email = varchar("email", 50)
    val relocation = varchar("relocation", 50)

    val freeForm = varchar("free_form", 50)

    override val primaryKey = PrimaryKey(id)
}

object DBTags : Table() {
    val id = long("id").autoIncrement()
    val resumeId = reference("resume_id", DBResumes.id)
    val tag = varchar("tag", 50)
    override val primaryKey = PrimaryKey(id)
}
