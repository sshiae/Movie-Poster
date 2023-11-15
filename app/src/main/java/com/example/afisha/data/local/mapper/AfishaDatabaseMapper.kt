package com.example.afisha.data.local.mapper

import com.example.afisha.data.local.entity.DatabaseCountry
import com.example.afisha.domain.model.Country

fun List<Country>.toCountryEntities(): List<DatabaseCountry> {
    return map { it.toCountryEntity() }
}

fun Country.toCountryEntity(): DatabaseCountry {
    return DatabaseCountry(
        id = id,
        name = name,
        code = code
    )
}