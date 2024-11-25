package com.cekduit.app.ui.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.cekduit.app.R
import com.cekduit.app.testdir.Transaction

class TransactionItemAdapter : RecyclerView.Adapter<TransactionItemAdapter.NestedViewHolder>() {
    private var items: List<Transaction> = listOf()

    fun setData(newItems: List<Transaction>) {
        items = newItems
        notifyDataSetChanged()
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): NestedViewHolder {
        val view = LayoutInflater.from(parent.context)
            .inflate(R.layout.transaction_item, parent, false)
        return NestedViewHolder(view)
    }

    override fun onBindViewHolder(holder: NestedViewHolder, position: Int) {
        holder.bind(items[position], isLast = position == items.size - 1)
    }

    override fun getItemCount() = items.size

    class NestedViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvCategoty: TextView = itemView.findViewById(R.id.tvCategory)
        private val tvAmount: TextView = itemView.findViewById(R.id.tvAmount)
        private val divider: View = itemView.findViewById(R.id.dividerBottom)

        fun bind(item: Transaction, isLast: Boolean = false) {
            tvAmount.text = item.amount.toString()
            tvCategoty.text = item.description
            divider.visibility = if (isLast) View.INVISIBLE else View.VISIBLE
        }
    }
}