package com.example.afisha.data.local

import androidx.paging.PagingData
import com.example.afisha.base.data.BaseRepository
import com.example.afisha.data.local.dao.AfishaDao
import com.example.afisha.data.local.mapper.toCountryEntities
import com.example.afisha.domain.model.Country
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Implementation of [AfishaLocalRepository]
 */
class AfishaLocalRepositoryImpl @Inject constructor(
    private val afishaDao: AfishaDao
) : BaseRepository(), AfishaLocalRepository {

    override suspend fun existsCountries(): Boolean {
        return afishaDao.existsCountries()
    }

    override suspend fun insertCountries(countries: List<Country>) {
        val convertedCountries = countries.toCountryEntities()
        convertedCountries.map { item ->
            afishaDao.insertCountry(item)
        }
    }

    override suspend fun getCountries(searchString: String): Flow<PagingData<Country>> {
        return doPagingRequest(afishaDao.getCountries(searchString))
    }
}