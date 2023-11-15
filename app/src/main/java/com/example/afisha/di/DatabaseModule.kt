package com.example.afisha.di

import android.content.Context
import androidx.room.Room
import com.example.afisha.common.database.AfishaDatabase
import com.example.afisha.data.local.dao.AfishaDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Singleton
    @Provides
    fun providesRoomDatabase(@ApplicationContext context: Context): AfishaDatabase {
        return Room.databaseBuilder(context, AfishaDatabase::class.java, AfishaDatabase.DATABASE_NAME)
            .build()
    }

    @Singleton
    @Provides
    fun provideAfishaDao(afishaDatabase: AfishaDatabase): AfishaDao {
        return afishaDatabase.afishaDao()
    }
}