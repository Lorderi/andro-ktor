package ru.lorderi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class Contacts(
    @SerialName("phone")
    val phone: String,
    @SerialName("email")
    val email: String,
)

@Serializable
data class CandidateInfo(
    @SerialName("name")
    val name: String,
    @SerialName("profession")
    val profession: String,
    @SerialName("sex")
    val sex: String,
    @SerialName("birth_date")
    val birthDate: String,
    @SerialName("contacts")
    val contact: Contacts,
    @SerialName("relocation")
    val relocation: String,
)

@Serializable
data class Education(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("resume_id")
    val resumeId: Long,
    @SerialName("type")
    val type: String,
    @SerialName("year_start")
    val yearStart: String,
    @SerialName("year_end")
    val yearEnd: String,
    @SerialName("description")
    val description: String,
)

@Serializable
data class JobExperience(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("resume_id")
    val resumeId: Long,
    @SerialName("date_start")
    val dateStart: String,
    @SerialName("date_end")
    val dateEnd: String,
    @SerialName("company_name")
    val companyName: String,
    @SerialName("description")
    val description: String,
)

@Serializable
data class Resume(
    @SerialName("id")
    val id: Long,
    @SerialName("candidate_info")
    val candidateInfo: CandidateInfo,
    @SerialName("education")
    val education: List<Education>,
    @SerialName("job_experience")
    val jobExperience: List<JobExperience>,
    @SerialName("free_form")
    val freeForm: String,
)

