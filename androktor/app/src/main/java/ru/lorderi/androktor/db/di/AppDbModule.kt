package ru.lorderi.androktor.db.di

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import ru.lorderi.androktor.dao.JobDao
import ru.lorderi.androktor.dao.ResumeDao
import ru.lorderi.androktor.db.AppDb
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class AppDbModule {
    @Provides
    fun ResumeDao(appDatabase: AppDb): ResumeDao {
        return appDatabase.resumeDao
    }
    @Provides
    fun JobDao(appDatabase: AppDb): JobDao {
        return appDatabase.jobDao
    }
    @Provides
    @Singleton
    fun provideAppDb(@ApplicationContext appContext: Context): AppDb {
        return Room.databaseBuilder(
            appContext,
            AppDb::class.java,
            "app_db"
        )
            .fallbackToDestructiveMigration()
            .allowMainThreadQueries()
            .build()
    }
}
