package com.example.afisha.domain.model

/**
 * Model класс, описывающий фильм
 */
data class Movie(
    val id: Int,
    val name: String,
    val genres: List<Genre>,
    val rating: Rating,
    val ageRating: String,
    val description: String,
    val shortDescription: String,
    val poster: Poster,
    val countries: List<Country>,
    val premiere: String?,
    val videos: Video?,
    val persons: List<Person>?,
    val movieLength: Int?,
    val year: Int?,
    val seriesLength: Int?,
    val totalSeriesLength: Int?
)