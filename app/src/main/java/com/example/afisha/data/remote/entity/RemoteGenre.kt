package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Genre
import com.google.gson.annotations.SerializedName

/**
 * Remote entity describing a genre.
 */
data class RemoteGenre(
    @SerializedName("name")
    val name: String
) : DomainMapper<Genre> {
    override fun mapToDomain(): Genre =
        Genre(
            name = name
        )
}