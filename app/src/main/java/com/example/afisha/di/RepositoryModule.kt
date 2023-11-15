package com.example.afisha.di

import com.example.afisha.data.local.AfishaLocalRepository
import com.example.afisha.data.local.AfishaLocalRepositoryImpl
import com.example.afisha.data.remote.AfishaRemoteRepository
import com.example.afisha.data.remote.AfishaRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Singleton
    @Binds
    abstract fun bindAfishaLocalRepository(
        afishaLocalRepositoryImpl: AfishaLocalRepositoryImpl
    ): AfishaLocalRepository

    @Singleton
    @Binds
    abstract fun bindAfishaRemoteRepository(
        afishaRemoteRepositoryImpl: AfishaRemoteRepositoryImpl
    ): AfishaRemoteRepository
}