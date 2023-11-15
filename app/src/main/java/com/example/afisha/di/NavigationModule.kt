package com.example.afisha.di

import com.github.terrakok.cicerone.Cicerone
import com.github.terrakok.cicerone.Cicerone.Companion.create
import com.github.terrakok.cicerone.NavigatorHolder
import com.github.terrakok.cicerone.Router
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object NavigationModule {

    @Provides
    @Singleton
    fun provideCicerone(): Cicerone<Router> {
        return create()
    }

    @Provides
    @Singleton
    fun provideRoute(cicerone: Cicerone<Router>): Router {
        return cicerone.router
    }

    @Provides
    @Singleton
    fun provideNavigatorHolder(cicerone: Cicerone<Router>): NavigatorHolder {
        return cicerone.getNavigatorHolder()
    }
}