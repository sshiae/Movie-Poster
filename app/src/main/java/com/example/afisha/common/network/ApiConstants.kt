package com.example.afisha.common.network

/**
 * Константы для работы с сетью
 */
object ApiConstants {

    /**
     * Базовый URL адрес
     */
    const val BASE_URL = "https://api.kinopoisk.dev/"

    /**
     * Версия API
     */
    const val API_VERSION = "v1.4"

    /**
     * Конечная точка для фильмов
     */
    const val FILM_URL = "$API_VERSION/movie"

    /**
     * Header ключ для подстановки API ключа
     */
    const val API_KEY_HEADER = "X-API-KEY"
}