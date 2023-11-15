package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Country
import com.google.gson.annotations.SerializedName

/**
 * Remote сущность, описывающая страну
 */
data class RemoteCountry(
    @SerializedName("name")
    val name: String
) : DataMapper<Country> {
    override fun mapToDomain(): Country =
        Country(
            id = 0,
            name = name,
            code = ""
        )
}