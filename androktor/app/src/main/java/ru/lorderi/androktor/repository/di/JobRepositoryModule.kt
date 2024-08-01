package ru.lorderi.androktor.repository.di

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import ru.lorderi.androktor.repository.JobRepository
import ru.lorderi.androktor.repository.NetworkJobRepository

@InstallIn(ViewModelComponent::class)
@Module
interface JobRepositoryModule {
    @Binds
    fun bindsJobRepository(impl: NetworkJobRepository): JobRepository
}
