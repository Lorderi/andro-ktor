package ru.lorderi.androktor.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Vacancy(
    @SerialName("id")
    val id: Long,
    @SerialName("profession")
    val profession: String,
    @SerialName("level")
    val level: String,
    @SerialName("salary")
    val salary: Int,
)

@Serializable
data class AdvancedClientCompany(
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

