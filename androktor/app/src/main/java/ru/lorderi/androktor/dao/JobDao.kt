package ru.lorderi.androktor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import ru.lorderi.androktor.entity.CompanyEntity
import ru.lorderi.androktor.entity.VacancyEntity

@Dao
interface JobDao {
    @Upsert
    fun saveCompany(companyEntity: CompanyEntity): Long
    @Upsert
    fun saveVacancy(vacancyEntity: VacancyEntity): Long
    @Query("SELECT * FROM Companies WHERE id = :id")
    fun getCompany(id: Long): CompanyEntity
    @Query("SELECT * FROM Companies")
    fun getCompanies(): List<CompanyEntity>
    @Query("SELECT * FROM Vacancy WHERE id = :id")
    fun getVacancy(id: Long): VacancyEntity
    @Query("SELECT * FROM Vacancy")
    fun getVacancies(): List<VacancyEntity>
    @Query("SELECT * FROM Vacancy WHERE companyId = :id")
    fun getVacanciesById(id: Long): List<VacancyEntity>
    @Delete
    fun deleteCompany(companyEntity: CompanyEntity)
    @Delete
    fun deleteVacancy(vacancyEntity: VacancyEntity)

}
