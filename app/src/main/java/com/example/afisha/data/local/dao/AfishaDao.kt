package com.example.afisha.data.local.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.example.afisha.data.local.entity.DatabaseCountry

@Dao
interface AfishaDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertCountry(country: DatabaseCountry)

    @Query("SELECT IFNULL(MAX(1), 0) FROM country_table")
    suspend fun existsCountries(): Boolean

    @Query("""
        SELECT   * 
        FROM     country_table 
        WHERE    LOWER(name) LIKE LOWER('%' || :name || '%')""")
    fun getCountries(name: String?): PagingSource<Int, DatabaseCountry>
}