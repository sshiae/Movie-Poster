package com.example.afisha.common

/**
 * Маппер данных в Domain модель
 */
interface DomainMapper<T> {

    /**
     * Маппит сущность в Domain модель
     */
    fun mapToDomain(): T
}