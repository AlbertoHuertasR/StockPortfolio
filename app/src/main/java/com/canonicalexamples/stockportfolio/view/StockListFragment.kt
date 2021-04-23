package com.canonicalexamples.stockportfolio.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.stockportfolio.R
import com.canonicalexamples.stockportfolio.app.StockPortfolioApp
import com.canonicalexamples.stockportfolio.databinding.FragmentTeasListBinding
import com.canonicalexamples.stockportfolio.model.Stock
import com.canonicalexamples.stockportfolio.model.StockAPI
import com.canonicalexamples.stockportfolio.model.StockAPIService
import com.canonicalexamples.stockportfolio.model.StockDatabase
import com.canonicalexamples.stockportfolio.util.observeEvent
import com.canonicalexamples.stockportfolio.viewmodels.StockListViewModel
import com.canonicalexamples.stockportfolio.viewmodels.StockListViewModelFactory
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch
import retrofit2.await

class StockListFragment : Fragment() {

    private lateinit var binding: FragmentTeasListBinding
    private val viewModel: StockListViewModel by viewModels {
        val app = activity?.application as StockPortfolioApp
        StockListViewModelFactory(app.database, app.webservice)
    }

    override fun onCreateView(
            inflater: LayoutInflater, container: ViewGroup?,
            savedInstanceState: Bundle?
    ): View? {
        // Inflate the layout for this fragment
        binding = FragmentTeasListBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)

        binding.recyclerView.adapter = StockListAdapter(viewModel = viewModel)
        binding.fab.setOnClickListener {
            viewModel.addButtonClicked()
        }

        binding.update.setOnClickListener {
            updateStocks()
        }



        viewModel.navigate.observeEvent(viewLifecycleOwner) { navigate ->
            if (navigate) {
                findNavController().navigate(R.id.action_FirstFragment_to_SecondFragment)
            }
        }



    }

    fun updateStocks() {

        val app = activity?.application as StockPortfolioApp
        val webservice: StockAPIService = app.webservice

        CoroutineScope(Dispatchers.Main + SupervisorJob()).launch {
            val database by lazy { StockDatabase.getInstance(requireContext()) }
            var stockService: StockAPI

            database.stockDao.apply {
                val list: List<Stock> = this.fetchStocks()
                for (stock: Stock in list) {
                    stockService = webservice.getStockPrice(stock.ticker).await()
                    stock.current_price = stockService.previous_close.toDouble()
                    stock.gains = stock.quantity * (stock.current_price - stock.buy_price)
                    this.update(stock)
                }
            }

            findNavController().navigate(R.id.action_FirstFragment_self)
        }

    }

}
