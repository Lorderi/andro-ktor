package ru.lorderi.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
class Tag(
    @SerialName("id")
    val id: Long = 0L,
    @SerialName("resumeId")
    val resumeId: Long = 0L,
    @SerialName("tag")
    val tag: String = "",
)

@Serializable
data class ClientResume(
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
    @SerialName("tags")
    val tags: List<Tag>,
) {
    companion object {
        fun toClientResume(resume: Resume, tags: List<Tag>): ClientResume {
            val newResume =
                with(resume) {
                    ClientResume(
                        id = id,
                        candidateInfo = candidateInfo,
                        education = education,
                        jobExperience = jobExperience,
                        freeForm = freeForm,
                        tags = tags
                    )
                }

            return newResume
        }
    }

    fun toResume(): Resume {
        val newResume =
            with(this) {
                Resume(
                    id = id,
                    candidateInfo = candidateInfo,
                    education = education,
                    jobExperience = jobExperience,
                    freeForm = freeForm
                )
            }

        return newResume
    }
}