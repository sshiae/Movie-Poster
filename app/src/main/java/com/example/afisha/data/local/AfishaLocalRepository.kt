package com.example.afisha.data.local

import androidx.paging.PagingData
import com.example.afisha.domain.model.Country
import kotlinx.coroutines.flow.Flow

/**
 * Local repository for accessing the database.
 */
interface AfishaLocalRepository {

    /**
     * Check if countries exist in the database.
     */
    suspend fun existsCountries(): Boolean

    /**
     * Save the list of countries [countries] to the database.
     */
    suspend fun insertCountries(countries: List<Country>)

    /**
     * Get all countries.
     */
    suspend fun getCountries(searchString: String): Flow<PagingData<Country>>
}