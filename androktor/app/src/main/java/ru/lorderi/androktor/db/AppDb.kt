package ru.lorderi.androktor.db

import androidx.room.AutoMigration
import androidx.room.Database
import androidx.room.RoomDatabase
import ru.lorderi.androktor.dao.JobDao
import ru.lorderi.androktor.dao.ResumeDao
import ru.lorderi.androktor.entity.CompanyEntity
import ru.lorderi.androktor.entity.VacancyEntity
import ru.lorderi.entity.EducationEntity
import ru.lorderi.entity.JobExperienceEntity
import ru.lorderi.entity.ResumesEntity
import ru.lorderi.entity.TagsEntity

//@Database(
//    entities = [EducationEntity::class,
//        JobExperienceEntity::class,
//        ResumesEntity::class,
//        TagsEntity::class,
//        CompanyEntity::class,
//        VacancyEntity::class],
//    version = 1,
//)
//
//abstract class AppDb : RoomDatabase() {
//    abstract val resumeDao: ResumeDao
//    abstract val jobDao: JobDao
//}

// Migration case

//@Database(
//    entities = [EducationEntity::class,
//        JobExperienceEntity::class,
//        ResumesEntity::class,
//        TagsEntity::class,
//        CompanyEntity::class,
//        VacancyEntity::class],
//    version = 2,
//    autoMigrations = [AutoMigration (from = 1, to = 2)]
//)
//
//abstract class AppDb : RoomDatabase() {
//    abstract val resumeDao: ResumeDao
//    abstract val jobDao: JobDao
//}

@Database(
    entities = [EducationEntity::class,
        JobExperienceEntity::class,
        ResumesEntity::class,
        TagsEntity::class,
        CompanyEntity::class,
        VacancyEntity::class],
    version = 3,
    autoMigrations = [AutoMigration (from = 2, to = 3)]
)

abstract class AppDb : RoomDatabase() {
    abstract val resumeDao: ResumeDao
    abstract val jobDao: JobDao
}