package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Video
import com.google.gson.annotations.SerializedName

/**
 * Remote entity describing video
 */
data class RemoteVideo(
    @SerializedName("trailers")
    val trailers: List<RemoteTrailer>?
) : DomainMapper<Video> {
    override fun mapToDomain(): Video {
        return Video(
            trailers = trailers?.map { it.mapToDomain() }
        )
    }
}