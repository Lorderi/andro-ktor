package ru.lorderi.androktor.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Query
import androidx.room.Upsert
import kotlinx.coroutines.flow.Flow
import ru.lorderi.androktor.model.JobExperience
import ru.lorderi.androktor.model.Resume
import ru.lorderi.entity.EducationEntity
import ru.lorderi.entity.JobExperienceEntity
import ru.lorderi.entity.ResumesEntity
import ru.lorderi.entity.TagsEntity

@Dao
interface ResumeDao {
    @Query("SELECT * FROM Resumes WHERE id = :id")
    suspend fun getResume(id: Long): ResumesEntity
    @Query("SELECT * FROM Tags WHERE resumeId = :id")
    suspend fun getTags(id: Long): List<TagsEntity>
    @Query("SELECT * FROM Education WHERE resumeId = :id")
    suspend fun getEducation(id: Long): List<EducationEntity>
    @Query("SELECT * FROM JobExperience WHERE resumeId = :id")
    suspend fun getJobExperience(id: Long): List<JobExperienceEntity>
    @Upsert
    fun saveResume(resume: ResumesEntity): Long
    @Upsert
    fun saveEducation(educationEntity: EducationEntity): Long
    @Upsert
    fun saveJobExperience(jobExperience: JobExperienceEntity): Long
    @Upsert
    fun saveTags(tagsEntity: TagsEntity): Long
    @Query("DELETE FROM Resumes WHERE id = :id")
    fun deleteResume(id: Long)
    @Query("DELETE FROM Education WHERE resumeId = :id")
    fun deleteEducation(id: Long)
    @Query("DELETE FROM JobExperience WHERE resumeId = :id")
    fun deleteJobExperience(id: Long)
    @Query("DELETE FROM Tags WHERE resumeId = :id")
    fun deleteTags(id: Long)
}
