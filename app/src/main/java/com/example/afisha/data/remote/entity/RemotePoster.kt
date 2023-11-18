package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Poster
import com.google.gson.annotations.SerializedName

/**
 * Remote сущность, описывающая картинку фильма
 */
data class RemotePoster(
    @SerializedName("url")
    val url: String,

    @SerializedName("previewUrl")
    val previewUrl: String
) : DomainMapper<Poster> {
    override fun mapToDomain(): Poster =
        Poster(
            url = url,
            previewUrl = previewUrl
        )
}