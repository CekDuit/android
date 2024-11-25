package com.cekduit.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.appcompat.view.menu.MenuView.ItemView
import androidx.recyclerview.widget.RecyclerView
import com.cekduit.app.R
import com.cekduit.app.testdir.Transaction
import java.text.NumberFormat
import java.util.Locale

class TransactionItemAdapter : RecyclerView.Adapter<TransactionItemAdapter.NestedViewHolder>() {
    private var items: List<Transaction> = listOf()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    fun setData(newItems: List<Transaction>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return NestedViewHolder(view, currencyFormat)
    }

    override fun onBindViewHolder(holder: NestedViewHolder, position: Int) {
        holder.bind(items[position], isLast = position == items.size - 1)
    }

    override fun getItemCount() = items.size

    class NestedViewHolder(itemView: View, private val currencyFormat: NumberFormat) : RecyclerView.ViewHolder(itemView) {
        private val tvCategory: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val divider: View = itemView.findViewById(R.id.dividerBottom)

        fun bind(item: Transaction, isLast: Boolean = false) {
            tvAmount.text = currencyFormat.format(item.amount)
            tvCategory.text = item.description
            divider.visibility = if (isLast) View.INVISIBLE else View.VISIBLE
        }
    }
}