package com.canonicalexamples.stockportfolio.view

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.canonicalexamples.stockportfolio.R
import com.canonicalexamples.stockportfolio.model.Stock
import com.canonicalexamples.stockportfolio.model.StockDatabase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

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

        view.findViewById<Button>(R.id.button_second).setOnClickListener {
            val database by lazy { StockDatabase.getInstance(requireContext()) }

            CoroutineScope(Dispatchers.IO + SupervisorJob()).launch {
                database.stockDao.apply {
                    this.create(stock = Stock(ticker = "TEST", buy_price = 30.0, quantity = 10, name = "TEST"))
                }
            }
            findNavController().navigate(R.id.action_SecondFragment_to_FirstFragment)
        }
    }
}
