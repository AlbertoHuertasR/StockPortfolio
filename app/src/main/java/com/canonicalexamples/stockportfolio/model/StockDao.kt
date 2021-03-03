package com.canonicalexamples.stockportfolio.model

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update

/**
 * 20210211. Initial version created by jorge
 * for a Canonical Examples training.
 *
 * Copyright 2021 Jorge D. Ortiz Fuentes
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
@Dao
interface StockDao {
    @Insert
    suspend fun create(stock: Stock)
    @Query("SELECT * FROM stock_table WHERE id = :id")
    suspend fun get(id: Int): Stock?
    @Query("SELECT * FROM stock_table")
    suspend fun fetchStocks(): List<Stock>
    @Update
    suspend fun update(stock: Stock)
    @Query("DELETE FROM stock_table WHERE id = :id")
    suspend fun delete(id: Int)
}
