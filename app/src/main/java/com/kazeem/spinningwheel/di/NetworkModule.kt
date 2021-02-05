package com.kazeem.spinningwheel.di

import android.content.Context
import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.kazeem.spinningwheel.data.datasource.WheelDataSource
import com.kazeem.spinningwheel.data.datasource.WheelDataSourceImpl
import com.kazeem.spinningwheel.data.repository.WheelRepositoryImpl
import com.kazeem.spinningwheel.data.service.WheelService
import com.kazeem.spinningwheel.domain.repository.WheelRepository
import dagger.Module
import dagger.Provides
import kotlinx.coroutines.Dispatchers
import okhttp3.Cache
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton

@Module class NetworkModule {

    private val BASE_URL = "http://mockbin.org/bin/"

    @Provides
    @Singleton
    fun provideGson(): Gson = GsonBuilder().serializeNulls().setLenient().setDateFormat("yyyy-MM-dd'T'HH:mm:ss").create()

    @Provides
    @Singleton
    fun provideCache(context: Context): Cache = Cache(context.cacheDir, (5 * 1024 * 1024).toLong())

    @Provides
    @Singleton
    fun provideOkHttpClient(cache: Cache): OkHttpClient =
        OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(60, TimeUnit.SECONDS)
            .writeTimeout(60, TimeUnit.SECONDS)
            .cache(cache)
            .build()

    @Provides
    @Singleton
    fun provideRetrofit(httpClient: OkHttpClient, gson: Gson): Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(httpClient)
        .addConverterFactory(GsonConverterFactory.create(gson))
        .build()

    //Service

    @Singleton
    @Provides
    fun providesService(retrofit: Retrofit): WheelService {
        return retrofit.create(WheelService::class.java)
    }

    // Remote data source

    @Singleton
    @Provides
    fun provideDataSource(wheelService: WheelService): WheelDataSource = WheelDataSourceImpl(wheelService)

    // Repository

    @Singleton
    @Provides
    fun provideRepository(wheelDataSource: WheelDataSource): WheelRepository = WheelRepositoryImpl(wheelDataSource)

    // Couroutine

    @Provides
    fun provideCoroutineDispatcherIO() = Dispatchers.IO

}