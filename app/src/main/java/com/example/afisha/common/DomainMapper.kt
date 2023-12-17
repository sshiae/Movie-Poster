package com.example.afisha.common

/**
 * Mapper for data to Domain model.
 */
interface DomainMapper<T> {

    /**
     * Maps the entity to the Domain model.
     */
    fun mapToDomain(): T
}