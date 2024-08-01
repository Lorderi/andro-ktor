package ru.lorderi.dao.facade

import io.ktor.http.*
import io.ktor.server.application.*
import io.ktor.server.response.*
import kotlinx.coroutines.runBlocking
import org.jetbrains.exposed.sql.ResultRow
import org.jetbrains.exposed.sql.SqlExpressionBuilder.eq
import org.jetbrains.exposed.sql.insert
import org.jetbrains.exposed.sql.selectAll
import org.jetbrains.exposed.sql.update
import ru.lorderi.dao.DatabaseSingleton.dbQuery
import ru.lorderi.dao.table.*
import ru.lorderi.model.*

class DAOFacadeImpl : DaoFacade {
    private fun resultRowToCompany(row: ResultRow, vacancies: List<Vacancy>): CompanyExtended {
        val activityString = row[DBCompanies.activity]
        val activity = try {
            ActivityType.valueOf(activityString)
        } catch (e: IllegalArgumentException) {
            throw Exception("Invalid activity type")
        }

        return CompanyExtended(
            id = row[DBCompanies.id],
            name = row[DBCompanies.name],
            activity = activity,
            vacancies = vacancies.filter { it.companyId == row[DBCompanies.id] },
            contacts = row[DBCompanies.contacts],
        )
    }

    override suspend fun addNewCompany(companyExtended: CompanyExtended): CompanyExtended? = dbQuery {
        val insertStatement = DBCompanies.insert {
            it[name] = companyExtended.name
            it[activity] = companyExtended.activity.name
            it[contacts] = companyExtended.contacts
        }
        insertStatement.resultedValues?.singleOrNull()?.let { resultRowToCompany(it, emptyList()) }
    }

    override suspend fun allCompanies(): List<CompanyExtended> {
        val vacancies = dbQuery {
            DBVacancies.selectAll().map(::resultRowToVacancy)
        }
        val companies = dbQuery {
            DBCompanies.selectAll().map { resultRowToCompany(it, vacancies) }
        }
        return companies
    }

    override suspend fun company(id: Long): CompanyExtended? {
        val vacancies = dbQuery {
            DBVacancies.selectAll().where(DBVacancies.companyId eq id).map(::resultRowToVacancy)
        }
        val company = dbQuery {
            DBCompanies
                .selectAll().where(DBCompanies.id eq id)
                .map { resultRowToCompany(it, vacancies) }
                .singleOrNull()
        }
        return company
    }

    override suspend fun addNewVacancy(vacancy: Vacancy): Vacancy? = dbQuery {
        val insertStatement = DBVacancies.insert {
            it[companyId] = vacancy.companyId
            it[company] = vacancy.company
            it[profession] = vacancy.profession
            it[level] = vacancy.level
            it[salary] = vacancy.salary
        }
        insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToVacancy)
    }

    private fun resultRowToVacancy(row: ResultRow) = Vacancy(
        id = row[DBVacancies.id],
        companyId = row[DBVacancies.companyId],
        company = row[DBVacancies.company],
        profession = row[DBVacancies.profession],
        level = row[DBVacancies.level],
        salary = row[DBVacancies.salary],
    )

    override suspend fun allVacancies(): List<Vacancy> = dbQuery {
        DBVacancies.selectAll().map(::resultRowToVacancy)
    }

    override suspend fun vacancy(id: Long): Vacancy? = dbQuery {
        DBVacancies
            .selectAll().where(DBVacancies.id eq id)
            .map(::resultRowToVacancy)
            .singleOrNull()
    }

    private fun resultRowToResume(
        row: ResultRow,
        educations: List<Education>,
        jobExperiences: List<JobExperience>
    ): Resume {
        val contacts = Contacts(
            phone = row[DBResumes.phone],
            email = row[DBResumes.email]
        )
        val candidateInfo = CandidateInfo(
            name = row[DBResumes.name],
            profession = row[DBResumes.profession],
            sex = row[DBResumes.sex],
            birthDate = row[DBResumes.birthDate],
            contact = contacts,
            relocation = row[DBResumes.relocation]
        )
        val resume = Resume(
            id = row[DBResumes.id],
            candidateInfo = candidateInfo,
            education = educations,
            jobExperience = jobExperiences,
            freeForm = row[DBResumes.freeForm]
        )
        return resume
    }

    override suspend fun allResumes(): List<Resume> {
        val jobExperiences = allJobExperiences()
        val education = allEducations()
        return dbQuery {
            DBResumes.selectAll()
                .map { row ->
                    val currentJobExperiences = jobExperiences.filter { it.resumeId == row[DBResumes.id] }
                    val currentEducation = education.filter { it.resumeId == row[DBResumes.id] }
                    resultRowToResume(row, currentEducation, currentJobExperiences)
                }
        }
    }

    override suspend fun resume(id: Long): Resume? {
        val educations = educations(id)
        val jobExperiences = jobExperiences(id)
        return dbQuery {
            DBResumes
                .selectAll().where(DBResumes.id eq id)
                .map { resultRowToResume(it, educations, jobExperiences) }
                .singleOrNull()
        }
    }

    suspend fun updateResume(resume: Resume): Boolean {
        val query = dbQuery {
            val candidateInfo = resume.candidateInfo
            val contacts = resume.candidateInfo.contact

            val insertStatement = DBResumes.update({ DBResumes.id eq resume.id }) {
                it[name] = candidateInfo.name
                it[profession] = candidateInfo.profession
                it[sex] = candidateInfo.sex
                it[birthDate] = candidateInfo.birthDate
                it[phone] = contacts.phone
                it[email] = contacts.email
                it[relocation] = candidateInfo.relocation
                it[freeForm] = resume.freeForm
            } > 0
            insertStatement
        }
        val jobExperiences = updateJobExperiences(resume)
        val educations = updateEducations(resume)
        return query || jobExperiences || educations
    }

    override suspend fun addNewResume(resume: Resume): Resume? {
        val checkThisResume = resume(resume.id)
        if (checkThisResume != null) {
            updateResume(resume)
            return resume(resume.id)
        } else {
            val query = dbQuery {
                val candidateInfo = resume.candidateInfo
                val contacts = resume.candidateInfo.contact
                val insertStatement = DBResumes.insert {
                    it[id] = resume.id
                    it[name] = candidateInfo.name
                    it[profession] = candidateInfo.profession
                    it[sex] = candidateInfo.sex
                    it[birthDate] = candidateInfo.birthDate
                    it[phone] = contacts.phone
                    it[email] = contacts.email
                    it[relocation] = candidateInfo.relocation
                    it[freeForm] = resume.freeForm
                }

                insertStatement.resultedValues?.singleOrNull()
            }
            val jobExperiences = addNewJobExperiences(resume)
            val educations = addNewEducation(resume)
            return query?.let { resultRowToResume(it, educations, jobExperiences) }
        }
    }

    private fun resultRowToJobExperience(row: ResultRow): JobExperience = JobExperience(
        id = row[DBJobExperience.id],
        resumeId = row[DBJobExperience.resumeId],
        dateStart = row[DBJobExperience.dateStart],
        dateEnd = row[DBJobExperience.dateEnd],
        companyName = row[DBJobExperience.companyName],
        description = row[DBJobExperience.description]
    )


    override suspend fun allJobExperiences(): List<JobExperience> = dbQuery {
        DBJobExperience
            .selectAll()
            .map(::resultRowToJobExperience)
    }

    private fun resultRowToTag(row: ResultRow): Tag = Tag(
        id = row[DBTags.id],
        resumeId = row[DBTags.resumeId],
        tag = row[DBTags.tag],
    )

    override suspend fun addNewTag(id: Long, tag: String): String? {
        val checkThisTag = tags(id)
        if (checkThisTag.isNotEmpty() && checkThisTag.find { it.tag == tag } != null) {
            return tag
        } else {
            val query = dbQuery {
                val insertStatement = DBTags.insert {
                    it[resumeId] = id
                    it[this.tag] = tag
                }
                insertStatement.resultedValues?.singleOrNull()
            }
            return query?.let { it[DBTags.tag] }
        }
    }

    override suspend fun tags(id: Long): List<Tag> {
        return dbQuery {
            DBTags.selectAll().where(DBTags.resumeId eq id)
                .map(::resultRowToTag)
        }
    }

    override suspend fun jobExperiences(resumeId: Long): List<JobExperience> = dbQuery {
        DBJobExperience
            .selectAll().where(DBJobExperience.resumeId eq resumeId)
            .map(::resultRowToJobExperience)
    }

    suspend fun updateJobExperiences(resume: Resume): Boolean = dbQuery {
        val jobExperience = resume.jobExperience
        var jobExperiencesIsOk = true

        jobExperience.forEach { experience ->
            val insertStatement = DBJobExperience.update({ DBJobExperience.id eq resume.id }) {
                it[dateStart] = experience.dateStart
                it[dateEnd] = experience.dateEnd
                it[companyName] = experience.companyName
                it[description] = experience.description
            } > 0
            if (!insertStatement) {
                jobExperiencesIsOk = false
            }
        }
        jobExperiencesIsOk
    }

    override suspend fun addNewJobExperiences(resume: Resume): List<JobExperience> = dbQuery {
        val jobExperience = resume.jobExperience
        val jobExperiences = mutableListOf<JobExperience>()
        jobExperience.forEach { experience ->
            val insertStatement = DBJobExperience.insert {
                it[resumeId] = resume.id
                it[dateStart] = experience.dateStart
                it[dateEnd] = experience.dateEnd
                it[companyName] = experience.companyName
                it[description] = experience.description
            }
            val result = insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToJobExperience)
            if (result != null) {
                jobExperiences.add(result)
            }
        }
        jobExperiences
    }

    private fun resultRowToEducation(row: ResultRow): Education = Education(
        id = row[DBEducation.id],
        resumeId = row[DBEducation.resumeId],
        type = row[DBEducation.type],
        yearStart = row[DBEducation.yearStart],
        yearEnd = row[DBEducation.yearEnd],
        description = row[DBEducation.description]
    )

    suspend fun allEducations(): List<Education> = dbQuery {
        DBEducation
            .selectAll()
            .map(::resultRowToEducation)
    }

    override suspend fun educations(resumeId: Long): List<Education> = dbQuery {
        DBEducation
            .selectAll().where(DBEducation.resumeId eq resumeId)
            .map(::resultRowToEducation)
    }

    suspend fun updateEducations(resume: Resume): Boolean = dbQuery {
        val education = resume.education
        var educationsIsOk = true

        education.forEach { edu ->
            val insertStatement = DBEducation.update {
                it[type] = edu.type
                it[yearStart] = edu.yearStart
                it[yearEnd] = edu.yearEnd
                it[description] = edu.description
            } > 0
            if (!insertStatement) {
                educationsIsOk = false
            }
        }
        educationsIsOk
    }

    override suspend fun addNewEducation(resume: Resume): List<Education> = dbQuery {
        val education = resume.education
        val educations = mutableListOf<Education>()
        education.forEach { edu ->
            val insertStatement = DBEducation.insert {
                it[resumeId] = resume.id
                it[type] = edu.type
                it[yearStart] = edu.yearStart
                it[yearEnd] = edu.yearEnd
                it[description] = edu.description
            }
            val result = insertStatement.resultedValues?.singleOrNull()?.let(::resultRowToEducation)
            if (result != null) {
                educations.add(result)
            }
        }
        educations
    }
}

val dao: DaoFacade = DAOFacadeImpl().apply {
    runBlocking {
        if (allCompanies().isEmpty()) {
            val companies = readCompanies()
            if (companies.listOfCompanies.isNotEmpty()) {
                companies.listOfCompanies.forEach {
                    addNewCompany(it)
                }
                val vacancies = ClientCompanies.toClientVacancies(companies)
                vacancies.listOfVacancies.forEach {
                    addNewVacancy(it)
                }
            } else {
                throw Exception("no companies in json")
            }
        }
        if (allResumes().isEmpty()) {
            val resume = readResume()
            if (resume != null) {
                val tags = analyzeResume(resume)

                addNewResume(resume)

                tags.forEach { tag ->
                    addNewTag(1, tag)
                }
            } else {
                throw Exception("no resume in json")
            }
        }
    }
}

