package com.canonicalexamples.stockportfolio.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.stockportfolio.R
import com.canonicalexamples.stockportfolio.app.StockPortfolioApp
import com.canonicalexamples.stockportfolio.model.Stock
import com.canonicalexamples.stockportfolio.model.StockAPI
import com.canonicalexamples.stockportfolio.model.StockAPIService
import com.canonicalexamples.stockportfolio.model.StockDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.await

/**
 * A simple [Fragment] subclass as the second destination in the navigation.
 */


class SecondFragment : Fragment() {



    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_second, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {


        super.onViewCreated(view, savedInstanceState)

        val app = activity?.application as StockPortfolioApp
        val webservice: StockAPIService = app.webservice

        val tickerEdit = view.findViewById<EditText>(R.id.ticker)
        val quantityEdit = view.findViewById<EditText>(R.id.quantity)
        val priceEdit = view.findViewById<EditText>(R.id.price)

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            val database by lazy { StockDatabase.getInstance(requireContext()) }
            var stock: StockAPI

            CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
                //read user form
                val ticker = tickerEdit.text.toString()
                val quantity = quantityEdit.text.toString().toInt()
                val price = priceEdit.text.toString().toDouble()
                stock = webservice.getStockPrice(ticker).await()

                val stockPrice = stock.previous_close.toDouble()
                val name = stock.name

                database.stockDao.apply {
                    this.create(stock = Stock(ticker = ticker, buy_price = price, quantity = quantity, name = name, current_price = stockPrice))
                }
            }



            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}
