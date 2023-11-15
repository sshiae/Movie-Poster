package com.example.afisha.data.remote.api

import com.example.afisha.common.network.ApiConstants
import com.example.afisha.data.remote.entity.response.ApiDocsResponse
import com.example.afisha.data.remote.entity.RemoteMovie
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API сервис с конечными точками Retrofit
 */
interface AfishaApi {

    /**
     * Получить список фильмов, сортированных по рейтингу
     *
     * @param page - страница
     * @param limit - количество фильмов на странице
     * @param country - страна
     * @param selectFields - поля в JSON ответе
     * @param notNullFields - название полей, которые не должны быть null
     */
    @GET(ApiConstants.FILM_URL)
    suspend fun getMoviesSortedByRating(
        @Query("page") page: Int,
        @Query("countries.name") country: String,
        @Query("limit") limit: Int = DEFAULT_PAGE_SIZE,
        @Query("sortField") sortField: String = DEFAULT_SORT_FIELD,
        @Query("sortType") sortType: String = DEFAULT_SORT_TYPE,
        @Query("selectFields") selectFields: List<String> = DEFAULT_SELECT_FIELDS,
        @Query("notNullFields") notNullFields: List<String> = DEFAULT_NOT_NULL_FIELDS,
    ): ApiDocsResponse<RemoteMovie>

    /**
     * Получить подробную информацию по фильму по ID [id]
     *
     * @param id - ID фильма
     */
    @GET(ApiConstants.FILM_URL)
    suspend fun getMovieById(
        @Path("id") id: Int
    ): RemoteMovie

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val DEFAULT_SORT_FIELD = "rating.imdb"
        const val DEFAULT_SORT_TYPE = "-1"
        val DEFAULT_SELECT_FIELDS = listOf(
            "name", "description", "shortDescription",
            "ageRating", "genres", "countries", "poster", "rating"
        )
        val DEFAULT_NOT_NULL_FIELDS = listOf(
            "name", "description", "shortDescription",
            "ageRating", "genres.name", "countries.name",
            "poster.url", "rating.imdb"
        )
    }
}