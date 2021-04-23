package com.canonicalexamples.stockportfolio.model

import androidx.room.Entity
import androidx.room.PrimaryKey

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
@Entity(tableName = "stock_table")
data class Stock(
    @PrimaryKey(autoGenerate = true)
    val id: Int = 0,
    val name: String = "",
    val quantity: Int = 0,
    val buy_price: Double = -1.0,
    val ticker: String = "",
    var current_price: Double = -1.0,
    var gains: Double = -1.0
    ) {
    val isValid: Boolean
        get() = name.isNotEmpty() && id >= 0 && buy_price > 0 && quantity > 0
}
