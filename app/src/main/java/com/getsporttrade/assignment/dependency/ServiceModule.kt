package com.getsporttrade.assignment.dependency

import android.content.Context
import androidx.room.Room
import com.getsporttrade.assignment.service.cache.LocalDatabase
import com.getsporttrade.assignment.service.network.NetworkService
import com.getsporttrade.assignment.service.network.StubInterceptor
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Singleton

/**
 * Hilt module for creating service layer objects
 */
@Module
@InstallIn(SingletonComponent::class)
object ServiceModule {

    /**
     * Singleton object for interfacing with the local data cache
     *
     * @param context Application context
     * @return the LocalDatabase singleton instance
     */
    @Singleton
    @Provides
    fun provideLocalDatabase(@ApplicationContext context: Context): LocalDatabase {
        return Room.inMemoryDatabaseBuilder(
            context,
            LocalDatabase::class.java
        )
            .fallbackToDestructiveMigration()
            .build()
    }

    /**
     * Singleton object for interfacing with the web service
     *
     * @return the NetworkService singleton instance
     */
    @Singleton
    @Provides
    fun provideNetworkService(): NetworkService {
        val gsonConverterFactory = GsonConverterFactory.create()
        val rxCallAdapterFactory = RxJava3CallAdapterFactory.create()
        val client = OkHttpClient.Builder()
            .addInterceptor(HttpLoggingInterceptor().apply { level = HttpLoggingInterceptor.Level.BODY })
            .addInterceptor(StubInterceptor())
            .build()

        return Retrofit.Builder()
            .baseUrl("https://getsporttrade.com")
            .addConverterFactory(gsonConverterFactory)
            .addCallAdapterFactory(rxCallAdapterFactory)
            .client(client)
            .build().create(NetworkService::class.java)
    }
}