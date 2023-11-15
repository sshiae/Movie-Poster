package com.example.afisha.data.remote.entity.response

import com.google.gson.annotations.SerializedName

/**
 * API ответ после запроса на сервер
 */
data class ApiDocsResponse<Entity>(
    @SerializedName("docs")
    val docs: List<Entity>,

    @SerializedName("pages")
    val pages: Int,

    @SerializedName("page")
    val page: Int
)