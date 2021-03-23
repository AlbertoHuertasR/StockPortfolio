package com.canonicalexamples.stockportfolio.model

import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Headers
import retrofit2.http.Query

/**
 * 20210218. Initial version created by jorge
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


interface StockAPIService {
    @Headers(
        "x-rapidapi-key: b86b491cc5mshff1436b3be13083p19ad85jsnb69470578e08",

        "x-rapidapi-host: twelve-data1.p.rapidapi.com"
    )
    @GET("/quote")
    fun getStockPrice(@Query("symbol")ticker: String): Call<StockAPI>

}