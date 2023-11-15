package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Rating
import com.google.gson.annotations.SerializedName

data class RemoteRating(
    @SerializedName("imdb")
    val imdb: String
) : DataMapper<Rating> {
    override fun mapToDomain(): Rating =
        Rating(
            imdb = imdb
        )
}