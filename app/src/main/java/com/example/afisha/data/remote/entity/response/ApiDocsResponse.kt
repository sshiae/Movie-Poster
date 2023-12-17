package com.example.afisha.data.remote.entity.response

import com.google.gson.annotations.SerializedName

/**
 * API response after a request to the server.
 */
data class ApiDocsResponse<Entity>(
    @SerializedName("docs")
    val docs: List<Entity>,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("page")
    val page: Int
)
