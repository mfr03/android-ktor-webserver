package com.example.webserverhmi.data.server.repository

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
abstract class ServerRepositoryModule {
    @Binds
    @Singleton
    abstract fun bindServerRepository(
        serverRepositoryImplementation: ServerRepositoryImplementation
    ): ServerRepository
}