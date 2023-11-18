package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Trailer
import com.google.gson.annotations.SerializedName

data class RemoteTrailer(
    @SerializedName("url")
    val url: String?
) : DomainMapper<Trailer> {
    override fun mapToDomain(): Trailer {
        return Trailer(
            url = url
        )
    }
}