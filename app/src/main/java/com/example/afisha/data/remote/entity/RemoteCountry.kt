package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Country
import com.google.gson.annotations.SerializedName

/**
 * Remote entity describing a country.
 */
data class RemoteCountry(
    @SerializedName("name")
    val name: String
) : DomainMapper<Country> {
    override fun mapToDomain(): Country =
        Country(
            id = 0,
            name = name,
            code = ""
        )
}
