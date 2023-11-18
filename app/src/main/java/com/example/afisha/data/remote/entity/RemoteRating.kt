package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Rating
import com.google.gson.annotations.SerializedName

data class RemoteRating(
    @SerializedName("imdb")
    val imdb: String
) : DomainMapper<Rating> {
    override fun mapToDomain(): Rating =
        Rating(
            imdb = imdb
        )
}