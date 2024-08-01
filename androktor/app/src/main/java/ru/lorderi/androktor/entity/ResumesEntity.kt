package ru.lorderi.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import ru.lorderi.androktor.model.CandidateInfo
import ru.lorderi.androktor.model.Contacts
import ru.lorderi.androktor.model.Education
import ru.lorderi.androktor.model.JobExperience
import ru.lorderi.androktor.model.Resume
import ru.lorderi.androktor.model.Tag

@Entity(tableName = "Education")
class EducationEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "resumeId")
    val resumeId: Long = 0L,
    @ColumnInfo(name = "type")
    val type: String = "",
    @ColumnInfo(name = "yearStart")
    val yearStart: String = "",
    @ColumnInfo(name = "yearEnd")
    val yearEnd: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
) {
    companion object {
        fun fromEducation(education: Education): EducationEntity = with(education) {
            EducationEntity(
                id = id,
                resumeId = resumeId,
                type = type,
                yearStart = yearStart,
                yearEnd = yearEnd,
                description = description
            )
        }
    }

    fun toEducation(): Education = Education(
        id = id,
        resumeId = resumeId,
        type = type,
        yearStart = yearStart,
        yearEnd = yearEnd,
        description = description
    )
}

@Entity(tableName = "JobExperience")
class JobExperienceEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "resumeId")
    val resumeId: Long = 0L,
    @ColumnInfo(name = "dateStart")
    val dateStart: String = "",
    @ColumnInfo(name = "dateEnd")
    val dateEnd: String = "",
    @ColumnInfo(name = "companyName")
    val companyName: String = "",
    @ColumnInfo(name = "description")
    val description: String = "",
) {
    companion object {
        fun fromJobExperience(jobExperience: JobExperience): JobExperienceEntity =
            with(jobExperience) {
                JobExperienceEntity(
                    id = id,
                    resumeId = resumeId,
                    dateStart = dateStart,
                    dateEnd = dateEnd,
                    description = description,
                    companyName = companyName,
                )
            }
    }

    fun toJobExperience(): JobExperience = JobExperience(
        id = id,
        resumeId = resumeId,
        dateStart = dateStart,
        dateEnd = dateEnd,
        description = description,
        companyName = companyName,
    )
}

@Entity(tableName = "Resumes")
class ResumesEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,

    // Candidate info
    @ColumnInfo(name = "name")
    val name: String = "",
    @ColumnInfo(name = "profession")
    val profession: String = "",
    @ColumnInfo(name = "sex")
    val sex: String = "",
    @ColumnInfo(name = "birthDate")
    val birthDate: String = "",

    // Contacts
    @ColumnInfo(name = "phone")
    val phone: String = "",
    @ColumnInfo(name = "email")
    val email: String = "",
    @ColumnInfo(name = "relocation")
    val relocation: String = "",

    @ColumnInfo(name = "freeForm")
    val freeForm: String = "",

    ) {
    companion object {
        fun fromResume(resume: Resume): ResumesEntity = with(resume) {
            ResumesEntity(
                id = id,
                name = candidateInfo.name,
                profession = candidateInfo.profession,
                sex = candidateInfo.sex,
                birthDate = candidateInfo.birthDate,
                phone = candidateInfo.contact.phone,
                email = candidateInfo.contact.email,
                relocation = candidateInfo.relocation,
                freeForm = freeForm,
            )
        }
    }

    fun toResume(
        education: List<Education>,
        jobExperience: List<JobExperience>,
        tags: List<Tag>
    ): Resume {
        val contact = Contacts(
            phone = phone,
            email = email,
        )
        val candidateInfo = CandidateInfo(
            name = name,
            profession = profession,
            sex = sex,
            birthDate = birthDate,
            contact = contact,
            relocation = relocation
        )
        return Resume(
            id = id,
            candidateInfo = candidateInfo,
            freeForm = freeForm,
            education = education,
            jobExperience = jobExperience,
            tags = tags
            )
    }
}

@Entity(tableName = "Tags")
class TagsEntity(
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    val id: Long = 0L,
    @ColumnInfo(name = "resumeId")
    val resumeId: Long = 0L,
    @ColumnInfo(name = "tag")
    val tag: String = "",
) {
    companion object {
        fun fromTags(tag: Tag): TagsEntity = with(tag) {
            TagsEntity(
                id = id,
                resumeId = resumeId,
                tag = this.tag
            )
        }
    }

    fun toTags(): Tag =
        Tag(
            id = id,
            resumeId = resumeId,
            tag = tag
        )
}
