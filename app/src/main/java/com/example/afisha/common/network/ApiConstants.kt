package com.example.afisha.common.network

/**
 * Constants for network operations.
 */
object ApiConstants {

    /**
     * Base URL address.
     */
    const val BASE_URL = "https://api.kinopoisk.dev/"

    /**
     * API version.
     */
    const val API_VERSION = "v1.4"

    /**
     * Endpoint for movies.
     */
    const val FILM_URL = "$API_VERSION/movie"

    /**
     * Header key for inserting the API key.
     */
    const val API_KEY_HEADER = "X-API-KEY"
}