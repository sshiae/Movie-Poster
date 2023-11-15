package com.example.afisha.common

/**
 * Маппер данных в Domain модель
 */
interface DataMapper<T> {

    /**
     * Маппит сущность в Domain модель
     */
    fun mapToDomain(): T
}