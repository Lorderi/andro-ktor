package ru.lorderi.dao.facade

import ru.lorderi.model.*

interface DaoFacade {
    suspend fun allCompanies(): List<CompanyExtended>
    suspend fun company(id: Long): CompanyExtended?
    suspend fun addNewCompany(companyExtended: CompanyExtended): CompanyExtended?
    suspend fun allVacancies(): List<Vacancy>
    suspend fun vacancy(id: Long): Vacancy?
    suspend fun addNewVacancy(vacancy: Vacancy): Vacancy?
    suspend fun allResumes(): List<Resume>
    suspend fun resume(id: Long): Resume?
    suspend fun addNewResume(resume: Resume): Resume?
    suspend fun addNewJobExperiences(resume: Resume): List<JobExperience>
    suspend fun addNewEducation(resume: Resume): List<Education>
    suspend fun jobExperiences(resumeId: Long): List<JobExperience>
    suspend fun educations(resumeId: Long): List<Education>
    suspend fun allJobExperiences(): List<JobExperience>
    suspend fun addNewTag(id: Long, tag: String): String?
    suspend fun tags(id: Long): List<Tag>
}

