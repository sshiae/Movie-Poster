package com.example.afisha.data.remote.entity

import com.example.afisha.common.DataMapper
import com.example.afisha.domain.model.Person
import com.google.gson.annotations.SerializedName

data class RemotePerson(
    @SerializedName("name")
    val name: String?
) : DataMapper<Person> {
    override fun mapToDomain(): Person {
        return Person(
            name = name
        )
    }
}