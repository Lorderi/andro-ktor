package ru.lorderi.androktor.api

import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.lorderi.androktor.model.AdvancedClientCompany
import ru.lorderi.androktor.model.ClientCompanies
import ru.lorderi.androktor.model.ClientVacancies
import ru.lorderi.androktor.model.ClientVacancy
import ru.lorderi.androktor.model.Resume

interface JobApi {
    @GET("api/companies")
    suspend fun getCompanies(): ClientCompanies

    @GET("api/companies/{id}")
    suspend fun getCompanyById(@Path("id") id: Long): AdvancedClientCompany

    @GET("api/vacancies")
    suspend fun getVacancies(): ClientVacancies

    @GET("api/vacancies/{id}")
    suspend fun getVacanciesById(@Path("id") id: Long): ClientVacancy

    @GET("api/resumes")
    suspend fun getResumes(): Resume

    @POST("api/resumes/{id}")
    suspend fun saveResume(@Path("id") id: Long, @Body resume: Resume): Resume

    @GET("api/resumes/{id}")
    suspend fun getResumeById(@Path("id") id: Long): Resume

    @GET("api/companies/{id}")
    suspend fun getTagsOfResumeById(@Path("id") id: Long): List<String>
}
