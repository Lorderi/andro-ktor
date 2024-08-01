package ru.lorderi.androktor.api.di

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.serialization.json.Json
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.create
import ru.lorderi.androktor.BuildConfig
import ru.lorderi.androktor.api.JobApi
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class ApiModule {
    private companion object {
        private val contentType = "application/json".toMediaType()
        private val json = Json { ignoreUnknownKeys = true }
    }

    @Singleton
    @Provides
    fun provideOkHttp(): OkHttpClient = OkHttpClient.Builder()
        .connectTimeout(5, TimeUnit.SECONDS)
        .let {
            if (BuildConfig.DEBUG) {
                it.addInterceptor(
                    HttpLoggingInterceptor().apply {
                        level = HttpLoggingInterceptor.Level.BODY
                    }
                )
            } else {
                it
            }
        }
        .build()

    @Singleton
    @Provides
    fun provideRetrofit(okHttpClient: OkHttpClient): Retrofit =
        Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl("http://10.0.2.2:8080")
            .addConverterFactory(json.asConverterFactory(contentType))
            .build()

    @Provides
    fun provideJobApi(retrofit: Retrofit): JobApi = retrofit.create()



}

//@Module
//class RoomModule(application: Application?) {
//    @Singleton
//    @Provides
//    fun RoomModule(application: Application?) {
//        return Room.databaseBuilder(application, DbClass::class.java, "database")
//            .fallbackToDestructiveMigration()
//            .build()
//    }
//}