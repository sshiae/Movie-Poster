package com.example.afisha.common.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.afisha.common.database.AfishaDatabase.Companion.DATABASE_VERSION
import com.example.afisha.data.local.dao.AfishaDao
import com.example.afisha.data.local.entity.DatabaseCountry

@Database(
    entities = [DatabaseCountry::class],
    version = DATABASE_VERSION
)
abstract class AfishaDatabase : RoomDatabase() {
    abstract fun afishaDao(): AfishaDao

    companion object {
        const val DATABASE_VERSION = 1
        const val DATABASE_NAME = "AfishaDatabase"
    }
}