package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
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

    @SerializedName("videos")
    val videos: RemoteVideo?,

    @SerializedName("persons")
    val persons: List<RemotePerson>?,

    @SerializedName("movieLength")
    val movieLength: Int?,

    @SerializedName("seriesLength")
    val seriesLength: Int?,

    @SerializedName("totalSeriesLength")
    val totalSeriesLength: Int?,

    @SerializedName("year")
    val year: Int?

) : DomainMapper<Movie> {
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
            videos = videos?.mapToDomain(),
            persons = persons?.map { it.mapToDomain() },
            movieLength = movieLength,
            year = year,
            seriesLength = seriesLength,
            totalSeriesLength = totalSeriesLength
        )
}