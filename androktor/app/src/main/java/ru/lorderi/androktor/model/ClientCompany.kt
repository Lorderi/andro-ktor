package ru.lorderi.androktor.model

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
data class ClientVacancy(
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
    // Migration case
    @SerialName("isFavourite")
    val isFavourite: Boolean? = false,
)

@Serializable
data class ClientVacancies(
    @SerialName("listOfVacancies")
    val listOfVacancies: List<ClientVacancy>,
)

@Serializable
data class ClientCompany(
    @SerialName("id")
    val id: Long,
    @SerialName("name")
    val name: String,
    @SerialName("field_of_activity")
    val activity: ActivityType,
)

@Serializable
data class ClientCompanies(
    @SerialName("listOfCompanies")
    val listOfCompanies: List<ClientCompany> = emptyList(),
)