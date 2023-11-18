package com.example.afisha.data.local.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.afisha.common.DomainMapper
import com.example.afisha.domain.model.Country

@Entity(tableName = "COUNTRY_TABLE")
data class DatabaseCountry(
    @PrimaryKey(autoGenerate = true)
    val id: Long,
    @ColumnInfo(name = "name")
    var name: String,
    @ColumnInfo(name = "code")
    var code: String
) : DomainMapper<Country> {
    override fun mapToDomain(): Country =
        Country(
            id = id,
            name = name,
            code = code
        )
}