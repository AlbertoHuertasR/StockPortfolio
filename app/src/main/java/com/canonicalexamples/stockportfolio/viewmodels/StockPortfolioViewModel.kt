package com.canonicalexamples.stockportfolio.viewmodels

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewModelScope
import com.canonicalexamples.stockportfolio.model.Stock
import com.canonicalexamples.stockportfolio.model.StockDatabase
import com.canonicalexamples.stockportfolio.model.StockAPIService
import com.canonicalexamples.stockportfolio.util.Event
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * 20210210. Initial version created by jorge
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
class StockListViewModel(private val database: StockDatabase, private val webservice: StockAPIService): ViewModel() {
    private val _navigate: MutableLiveData<Event<Boolean>> = MutableLiveData()
    val navigate: LiveData<Event<Boolean>> = _navigate
    private var stockList = listOf<Stock>()
    data class Item(val name: String, val buy_price: Double)

    init {
        viewModelScope.launch(Dispatchers.IO) {
            stockList = database.stockDao.fetchStocks()
        }
    }

    val numberOfItems: Int
        get() = stockList.count()

    fun addButtonClicked() {
        _navigate.value = Event(true)
    }

    fun getItem(n: Int) = Item(name = stockList[n].name, buy_price = stockList[n].buy_price)

    fun onClickItem(n: Int) {
        println("Item $n clicked")
        viewModelScope.launch(Dispatchers.IO) {
            val stockPrice = webservice.getStockPrice(stockList[n].ticker).await()
            println("stock: ${stockPrice.previous_close}")
        }
    }
}

class StockListViewModelFactory(private val database: StockDatabase, private val webservice: StockAPIService): ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        if (modelClass.isAssignableFrom(StockListViewModel::class.java)) {
            @Suppress("UNCHECKED_CAST")
            return StockListViewModel(database, webservice) as T
        }
        throw IllegalArgumentException("Unknown ViewModel class")
    }
}
