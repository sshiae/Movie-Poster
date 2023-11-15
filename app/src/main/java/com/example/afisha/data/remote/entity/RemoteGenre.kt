package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Genre
import com.google.gson.annotations.SerializedName

/**
 * Remote сущность, описывающая жанр
 */
data class RemoteGenre(
    @SerializedName("name")
    val name: String
) : DataMapper<Genre> {
    override fun mapToDomain(): Genre =
        Genre(
            name = name
        )
}