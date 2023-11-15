package com.example.afisha.data.remote

import androidx.paging.PagingData
import com.example.afisha.domain.model.Movie
import kotlinx.coroutines.flow.Flow

/**
 * Удаленный репозиторий для работы с сетью
 */
interface AfishaRemoteRepository {

    /**
     * Используется для получения списка фильмов [Movie] сортированных по рейтингу
     *
     * @param country - страна
     */
    suspend fun getMoviesSortedByRating(country: String): Flow<PagingData<Movie>>

    /**
     * Используется для получения фильма по [id]
     *
     * @param id - ID фильма
     */
    suspend fun getMovieById(id: Int): Movie
}