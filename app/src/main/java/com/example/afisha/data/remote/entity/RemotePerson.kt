package com.example.afisha.data.remote.entity

import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Person
import com.google.gson.annotations.SerializedName

/**
 * Remote entity describing person
 */
data class RemotePerson(
    @SerializedName("name")
    val name: String?,

    @SerializedName("enProfession")
    val enProfession: String?
) : DomainMapper<Person> {
    override fun mapToDomain(): Person {
        return Person(
            name = name,
            enProfession = enProfession
        )
    }
}