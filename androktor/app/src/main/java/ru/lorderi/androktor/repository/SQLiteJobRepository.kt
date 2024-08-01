package ru.lorderi.androktor.repository

import ru.lorderi.androktor.dao.JobDao
import ru.lorderi.androktor.dao.ResumeDao
import ru.lorderi.androktor.entity.CompanyEntity
import ru.lorderi.androktor.entity.VacancyEntity
import ru.lorderi.androktor.model.AdvancedClientCompany
import ru.lorderi.androktor.model.ClientCompany
import ru.lorderi.androktor.model.ClientVacancy
import ru.lorderi.androktor.model.Resume
import ru.lorderi.entity.EducationEntity
import ru.lorderi.entity.JobExperienceEntity
import ru.lorderi.entity.ResumesEntity
import ru.lorderi.entity.TagsEntity
import javax.inject.Inject

class SQLiteJobRepository @Inject constructor(
    private val dao: JobDao,
) {
    fun getCompanies() = dao.getCompanies().map {
        it.toCompany()
    }

    fun getCompany(id: Long) = dao.getCompany(id)
    fun getVacancies() = dao.getVacancies().map {
        it.toVacancy()
    }
    fun getVacancy(id: Long) = dao.getVacancy(id)

    fun getVacanciesById(id: Long) = dao.getVacanciesById(id).map {
        it.toVacancy()
    }
    fun saveCompany(company: ClientCompany) =
        dao.saveCompany(CompanyEntity.fromCompany(company))

    fun saveVacancy(vacancy: ClientVacancy) =
        dao.saveVacancy(VacancyEntity.fromVacancy(vacancy))

    fun deleteCompany(id: Long) {
//        dao.deleteCompany(id)
    }

    fun deleteVacancy(id: Long) {
//        dao.deleteVacancy(id)
    }
}
