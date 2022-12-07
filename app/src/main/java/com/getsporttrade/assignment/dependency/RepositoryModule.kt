package com.getsporttrade.assignment.dependency

import com.getsporttrade.assignment.repository.PositionRepository
import com.getsporttrade.assignment.service.cache.LocalDatabase
import com.getsporttrade.assignment.service.network.NetworkService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

/**
 * Hilt module for creating repository layer objects
 */
@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    /**
     * Singleton component for accessing position data
     *
     * @param networkService Service layer object for accessing web service
     * @param dbService Service layout object for accessing local data cache
     * @return the PositionRepository singleton instance
     */
    @Singleton
    @Provides
    fun providePositionRepository(networkService: NetworkService, dbService: LocalDatabase): PositionRepository {
        return PositionRepository(networkService, dbService)
    }
}