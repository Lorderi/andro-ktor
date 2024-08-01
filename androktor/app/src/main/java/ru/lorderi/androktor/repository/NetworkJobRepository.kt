package ru.lorderi.androktor.repository

import kotlinx.coroutines.delay
import ru.lorderi.androktor.api.JobApi
import ru.lorderi.androktor.model.AdvancedClientCompany
import ru.lorderi.androktor.model.ClientCompanies
import ru.lorderi.androktor.model.ClientVacancies
import ru.lorderi.androktor.model.ClientVacancy
import ru.lorderi.androktor.model.Resume
import javax.inject.Inject

class NetworkJobRepository @Inject constructor(private val api: JobApi) : JobRepository {
    override suspend fun getCompanies(): ClientCompanies = api.getCompanies()
    override suspend fun getCompanyById(id: Long): AdvancedClientCompany = api.getCompanyById(id)
    override suspend fun getVacancies(): ClientVacancies = api.getVacancies()
    override suspend fun getVacanciesById(id: Long): ClientVacancy = api.getVacanciesById(id)
    override suspend fun getResumes(): Resume = api.getResumes()
    override suspend fun saveResume(id: Long, resume: Resume): Resume = api.saveResume(id, resume)
    override suspend fun getResumeById(id: Long): Resume = api.getResumeById(id)
    override suspend fun getTagsOfResumeById(id: Long): List<String> = api.getTagsOfResumeById(id)

}
