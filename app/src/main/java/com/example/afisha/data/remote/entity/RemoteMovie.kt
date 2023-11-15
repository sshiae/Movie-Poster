package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Movie
import com.google.gson.annotations.SerializedName

/**
 * Remote сущность, описывающая фильм
 */
data class RemoteMovie(

    @SerializedName("id")
    val id: Int,

    @SerializedName("name")
    val name: String,

    @SerializedName("genres")
    val genres: List<RemoteGenre>,

    @SerializedName("rating")
    val rating: RemoteRating,

    @SerializedName("ageRating")
    val ageRating: String,

    @SerializedName("description")
    val description: String,

    @SerializedName("shortDescription")
    val shortDescription: String,

    @SerializedName("poster")
    val poster: RemotePoster,

    @SerializedName("countries")
    val countries: List<RemoteCountry>,

    @SerializedName("premiere.country")
    val premiere: String?,

    @SerializedName("videos.trailers")
    val trailers: List<RemoteTrailer>?,

    @SerializedName("persons")
    val persons: List<RemotePerson>?,

    @SerializedName("totalSeriesLength")
    val totalSeriesLength: Int?,

    @SerializedName("year")
    val year: Int?

) : DataMapper<Movie> {
    override fun mapToDomain(): Movie =
        Movie(
            id = id,
            name = name,
            genres = genres.map { it.mapToDomain() },
            rating = rating.mapToDomain(),
            ageRating = ageRating,
            description = description,
            shortDescription = shortDescription,
            poster = poster.mapToDomain(),
            countries = countries.map { it.mapToDomain() },
            premiere = premiere,
            trailers = trailers?.map { it.mapToDomain() },
            persons = persons?.map { it.mapToDomain() },
            totalSeriesLength = totalSeriesLength,
            year = year
        )
}