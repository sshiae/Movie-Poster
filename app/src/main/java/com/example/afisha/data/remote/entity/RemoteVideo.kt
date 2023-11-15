package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Video
import com.google.gson.annotations.SerializedName

data class RemoteVideo(
    @SerializedName("trailers")
    val trailers: List<RemoteTrailer>?
) : DataMapper<Video> {
    override fun mapToDomain(): Video {
        return Video(
            trailers = trailers?.map { it.mapToDomain() }
        )
    }
}