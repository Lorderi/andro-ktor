package ru.lorderi.androktor.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lorderi.androktor.model.ClientVacancy

@Entity(tableName = "Vacancy")
class VacancyEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "companyId")
    val companyId: Long = 0L,
    @ColumnInfo(name = "company")
    val company: String = "",
    @ColumnInfo(name = "profession")
    val profession: String = "",
    @ColumnInfo(name = "level")
    val level: String = "",
    @ColumnInfo(name = "salary")
    val salary: Int = 0,

    // Migration case
    @ColumnInfo(name = "isFavourite")
    val isFavourite: Boolean? = false,
) {
    companion object {
        fun fromVacancy(clientVacancy: ClientVacancy): VacancyEntity = with(clientVacancy) {
            VacancyEntity(
                id = id,
                companyId = companyId,
                company = company,
                profession = profession,
                level = level,
                salary = salary,
            )
        }
    }

    fun toVacancy(): ClientVacancy = ClientVacancy(
        id = id,
        companyId = companyId,
        company = company,
        profession = profession,
        level = level,
        salary = salary,
        isFavourite = isFavourite ?: false,
    )
}