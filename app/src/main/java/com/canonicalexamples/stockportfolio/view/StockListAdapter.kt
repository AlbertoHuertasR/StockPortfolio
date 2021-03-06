package com.canonicalexamples.stockportfolio.view

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.canonicalexamples.stockportfolio.databinding.ItemStockBinding
import com.canonicalexamples.stockportfolio.viewmodels.StockListViewModel

/**
 * 20210208. Initial version created by jorge
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
class StockListAdapter(private val viewModel: StockListViewModel): RecyclerView.Adapter<StockListAdapter.TeaItemViewHolder>() {

    class TeaItemViewHolder(private val viewModel: StockListViewModel, binding: ItemStockBinding): RecyclerView.ViewHolder(binding.root), View.OnClickListener {
        val stockName = binding.stockName
        val stockBuyPrice = binding.stockBuyPrice
        val stockCurrentPrice = binding.stockCurrentPrice
        val stockGains = binding.stockGains

        init {
            binding.root.setOnClickListener(this)
        }
        override fun onClick(v: View?) {
            viewModel.onClickItem(layoutPosition)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): TeaItemViewHolder =
            TeaItemViewHolder(viewModel, ItemStockBinding.inflate(LayoutInflater.from(parent.context)))

    override fun onBindViewHolder(holder: TeaItemViewHolder, position: Int) {
        val stock = viewModel.getItem(position)
        holder.stockName.text = "${stock.name}"
        holder.stockBuyPrice.text = "${stock.buy_price}"
        holder.stockCurrentPrice.text = "${stock.current_price}"
        holder.stockGains.text = String.format("%.2f", stock.gains)

    }

    override fun getItemCount(): Int = viewModel.numberOfItems
}
