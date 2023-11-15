package com.example.afisha.di

import com.example.afisha.data.local.AfishaLocalRepository
import com.example.afisha.data.local.AfishaLocalRepositoryImpl
import com.example.afisha.data.remote.AfishaRemoteRepository
import com.example.afisha.data.remote.AfishaRemoteRepositoryImpl
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ViewModelComponent
import dagger.hilt.android.scopes.ViewModelScoped

@Module
@InstallIn(ViewModelComponent::class)
abstract class RepositoryModule {

    @ViewModelScoped
    @Binds
    abstract fun bindAfishaLocalRepository(
        afishaLocalRepositoryImpl: AfishaLocalRepositoryImpl
    ): AfishaLocalRepository

    @ViewModelScoped
    @Binds
    abstract fun bindAfishaRemoteRepository(
        afishaRemoteRepositoryImpl: AfishaRemoteRepositoryImpl
    ): AfishaRemoteRepository
}