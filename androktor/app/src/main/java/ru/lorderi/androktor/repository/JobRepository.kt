package ru.lorderi.androktor.repository

import ru.lorderi.androktor.model.AdvancedClientCompany
import ru.lorderi.androktor.model.ClientCompanies
import ru.lorderi.androktor.model.ClientVacancies
import ru.lorderi.androktor.model.ClientVacancy
import ru.lorderi.androktor.model.Resume

interface JobRepository {
    suspend fun getCompanies(): ClientCompanies
    suspend fun getCompanyById(id: Long): AdvancedClientCompany
    suspend fun getVacancies(): ClientVacancies
    suspend fun getVacanciesById(id: Long): ClientVacancy
    suspend fun getResumes(): Resume
    suspend fun saveResume(id: Long, resume: Resume): Resume
    suspend fun getResumeById(id: Long): Resume
    suspend fun getTagsOfResumeById(id: Long): List<String>
}
