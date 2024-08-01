package ru.lorderi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
enum class ActivityType(val value: String) {
    @SerialName("IT")
    IT("IT"),

    @SerialName("banking")
    BANKING("Banking"),

    @SerialName("public services")
    PUBLIC_SERVICES("Public services"),
}

@Serializable
data class Vacancy(
    @SerialName("id")
    val id: Long,
    @SerialName("company")
    val company: String,
    @SerialName("company_id")
    val companyId: Long,
    @SerialName("profession")
    val profession: String,
    @SerialName("level")
    val level: String,
    @SerialName("salary")
    val salary: Int,
)

@Serializable
data class Vacancies(
    @SerialName("listOfVacancies")
    val listOfVacancies: List<Vacancy> = emptyList(),
)

@Serializable
data class CompanyExtended(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("field_of_activity")
    val activity: ActivityType,
    @SerialName("vacancies")
    val vacancies: List<Vacancy>,
    @SerialName("contacts")
    val contacts: String,
)

@Serializable
data class CompaniesExtended(
    @SerialName("listOfCompanies")
    val listOfCompanies: List<CompanyExtended> = emptyList(),
)
