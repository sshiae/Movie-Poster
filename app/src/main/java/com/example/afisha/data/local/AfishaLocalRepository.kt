package com.example.afisha.data.local

import androidx.paging.PagingData
import com.example.afisha.domain.model.Country
import kotlinx.coroutines.flow.Flow

/**
 * Локальный репозиторий для доступа к базе данных
 */
interface AfishaLocalRepository {

    /**
     * Существуют ли страны в базе данных
     */
    suspend fun existsCountries(): Boolean

    /**
     * Сохранить список городов [countries] в базу данных
     */
    suspend fun insertCountries(countries: List<Country>)

    /**
     * Получить все города
     */
    suspend fun getCountries(searchString: String): Flow<PagingData<Country>>
}