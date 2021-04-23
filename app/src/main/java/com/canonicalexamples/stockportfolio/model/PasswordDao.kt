package com.canonicalexamples.stockportfolio.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface PasswordDao {
    @Insert
    suspend fun create(password:Password)

    @Query("SELECT * FROM passwordTable WHERE value = :pass")
    suspend fun get(pass:Int): Password?

    @Query("SELECT * FROM passwordTable")
    suspend fun count(): List<Password>


}