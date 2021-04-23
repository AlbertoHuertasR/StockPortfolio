package com.canonicalexamples.stockportfolio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "passwordTable")
data class Password (
    @PrimaryKey (autoGenerate = true)
    val id: Int = 0,

    val value: Int

)

