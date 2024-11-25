package com.cekduit.app.ui.adapter

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.cekduit.app.R
import com.cekduit.app.testdir.TransactionList
import java.text.NumberFormat
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionsListAdapter : RecyclerView.Adapter<TransactionsListAdapter.ParentViewHolder>() {
    private var items: List<TransactionList> = listOf()
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("in", "ID"))

    fun setData(newItems: List<TransactionList>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ParentViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transactions_list, parent, false)
        return ParentViewHolder(view, currencyFormat)
    }

    override fun onBindViewHolder(holder: ParentViewHolder, position: Int) {
        holder.bind(items[position])
    }

    override fun getItemCount() = items.size

    class ParentViewHolder(itemView: View, private val currencyFormat: NumberFormat) : RecyclerView.ViewHolder(itemView) {
        private val tvDate: TextView = itemView.findViewById(R.id.tvDate)
        private val rvNested: RecyclerView = itemView.findViewById(R.id.rvTransactionItems)
        private val nestedAdapter = TransactionItemAdapter()
        private val totalIncome: TextView = itemView.findViewById(R.id.totalIncome)
        private val totalExpense: TextView = itemView.findViewById(R.id.totalExpense)

        init {
            rvNested.apply {
                layoutManager = LinearLayoutManager(itemView.context)
                adapter = nestedAdapter
            }
        }

        fun bind(item: TransactionList) {
            val inputFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH)
            val outputFormatter = DateTimeFormatter.ofPattern("d MMMM yyyy")
            tvDate.text = LocalDate.parse(item.date, inputFormatter).format(outputFormatter)
            totalIncome.text = currencyFormat.format(item.totalIncome)
            totalExpense.text = currencyFormat.format(item.totalExpense)
            nestedAdapter.setData(item.transactions)
        }
    }
}