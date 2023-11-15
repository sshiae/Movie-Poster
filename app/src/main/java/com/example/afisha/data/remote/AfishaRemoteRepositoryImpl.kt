package com.example.afisha.data.remote

import androidx.paging.PagingData
import com.example.afisha.base.data.BaseRepository
import com.example.afisha.data.remote.api.AfishaApi
import com.example.afisha.data.remote.paging.MovieTopPagingSource
import com.example.afisha.domain.model.Movie
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class AfishaRemoteRepositoryImpl @Inject constructor(
    private val afishaApi: AfishaApi
) : BaseRepository(), AfishaRemoteRepository {

    override suspend fun getMoviesSortedByRating(country: String): Flow<PagingData<Movie>> {
        return doPagingNetworkRequest(MovieTopPagingSource(afishaApi, country))
    }

    override suspend fun getMovieById(id: Int): Movie {
        return afishaApi.getMovieById(id).mapToDomain()
    }
}