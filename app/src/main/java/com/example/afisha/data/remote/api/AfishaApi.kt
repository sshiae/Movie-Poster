package com.example.afisha.data.remote.api

import com.example.afisha.common.network.ApiConstants
import com.example.afisha.data.remote.entity.RemoteMovie
import com.example.afisha.data.remote.entity.response.ApiDocsResponse
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * API service with Retrofit endpoints.
 */
interface AfishaApi {

    /**
     * Get a list of movies sorted by rating.
     *
     * @param page - page
     * @param limit - number of movies per page
     * @param country - country
     * @param selectFields - fields in the JSON response
     * @param notNullFields - names of fields that should not be null
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
     * Get detailed information about a movie by ID [id].
     *
     * @param id - movie ID
     */
    @GET("${ApiConstants.FILM_URL}/{id}")
    suspend fun getMovieById(
        @Path("id") id: Int
    ): RemoteMovie

    companion object {
        const val DEFAULT_PAGE_SIZE = 10
        const val DEFAULT_SORT_FIELD = "rating.imdb"
        const val DEFAULT_SORT_TYPE = "-1"
        val DEFAULT_SELECT_FIELDS = listOf(
            "name", "description", "shortDescription",
            "ageRating", "genres", "countries", "poster", "rating",
            "id"
        )
        val DEFAULT_NOT_NULL_FIELDS = listOf(
            "name", "description", "shortDescription",
            "ageRating", "genres.name", "countries.name",
            "poster.url", "rating.imdb", "id"
        )
    }
}