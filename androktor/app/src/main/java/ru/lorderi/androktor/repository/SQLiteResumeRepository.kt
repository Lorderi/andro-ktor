package ru.lorderi.androktor.repository

import ru.lorderi.androktor.dao.ResumeDao
import ru.lorderi.androktor.model.Resume
import ru.lorderi.entity.EducationEntity
import ru.lorderi.entity.JobExperienceEntity
import ru.lorderi.entity.ResumesEntity
import ru.lorderi.entity.TagsEntity
import javax.inject.Inject

class SQLiteResumeRepository @Inject constructor(
    private val dao: ResumeDao,
) {
    suspend fun getResume(id: Long): Resume {
        val education = dao.getEducation(id).map {
            it.toEducation()
        }
        val jobExperience = dao.getJobExperience(id).map {
            it.toJobExperience()
        }
        val tag = dao.getTags(id).map {
            it.toTags()
        }

        return dao.getResume(id).toResume(education, jobExperience, tag)
    }

    fun saveResume(resume: Resume) {
        dao.saveResume(ResumesEntity.fromResume(resume))
        resume.education.forEach { education ->
            dao.saveEducation(EducationEntity.fromEducation(education))
        }
        resume.jobExperience.forEach { jobExperience ->
            dao.saveJobExperience(JobExperienceEntity.fromJobExperience(jobExperience))
        }
        resume.tags.forEach { tag ->
            dao.saveTags(TagsEntity.fromTags(tag))
        }
    }

    fun deleteResume(id: Long) {
        dao.deleteResume(id)
        dao.deleteEducation(id)
        dao.deleteJobExperience(id)
        dao.deleteTags(id)
    }
}
