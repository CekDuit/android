package com.cekduit.app.ui.transactions

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.cekduit.app.R
import com.cekduit.app.databinding.FragmentTransactionsBinding
import com.cekduit.app.testdir.DummyData
import com.cekduit.app.testdir.Transaction
import com.cekduit.app.testdir.TransactionDummy
import com.cekduit.app.testdir.TransactionList
import com.cekduit.app.testdir.TransactionType
import com.cekduit.app.ui.adapter.TransactionItemAdapter
import com.cekduit.app.ui.adapter.TransactionsListAdapter
import com.google.android.material.dialog.MaterialAlertDialogBuilder
import java.text.NumberFormat
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
      ViewModelProvider(this).get(TransactionsViewModel::class.java)

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                menuInflater.inflate(R.menu.transaction_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.rvTransactionsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val dummyData = TransactionDummy().dummyData
            val dataAdapter = TransactionsListAdapter() { transaction ->
                // Handle item click
                Log.d("Transaction", "Item clicked: $transaction")
                showTransactionDetailDialog(transaction)
            }
            adapter = dataAdapter
            dataAdapter.setData(listOf(
                dummyData,
                dummyData,
                dummyData,
                dummyData,
            ))

        }

        return root
    }

    private fun showTransactionDetailDialog(transaction: Transaction) {
        val dialogView = LayoutInflater.from(requireContext()).inflate(R.layout.dialog_transaction_detail, null)

        val tvDescription: TextView = dialogView.findViewById(R.id.tvTransactionDescription)
        val tvAmount: TextView = dialogView.findViewById(R.id.tvTransactionAmount)
        val tvDate: TextView = dialogView.findViewById(R.id.tvTransactionDate)

        tvDescription.text = transaction.description
        tvAmount.text = NumberFormat.getCurrencyInstance(Locale("in", "ID")).format(transaction.amount)
        tvDate.text = transaction.timestamp.toString()

        MaterialAlertDialogBuilder(requireContext())
            .setTitle("Transaction Detail")
            .setView(dialogView)
            .setPositiveButton("Close") { dialog, _ ->
                dialog.dismiss()
            }
            .show()
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}