package com.cekduit.app.ui.transactions

import android.os.Bundle
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
import java.time.format.DateTimeFormatter
import java.util.Locale

class TransactionsFragment : Fragment() {

    private var _binding: FragmentTransactionsBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val transactionsViewModel =
            ViewModelProvider(this).get(TransactionsViewModel::class.java)

        _binding = FragmentTransactionsBinding.inflate(inflater, container, false)
        val root: View = binding.root

        // setup menu for this fragment
        requireActivity().addMenuProvider(object : MenuProvider {
            override fun onCreateMenu(menu: Menu, menuInflater: MenuInflater) {
                // Inflate menu Resource
                menuInflater.inflate(R.menu.transaction_fragment_menu, menu)
            }

            override fun onMenuItemSelected(menuItem: MenuItem): Boolean {
                return when (menuItem.itemId) {
                    R.id.action_search -> {
                        // Handle search action
                        true
                    }
                    else -> false
                }
            }
        }, viewLifecycleOwner, Lifecycle.State.RESUMED)

        binding.rvTransactionsList.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val dummyData = TransactionDummy().dummyData
            val dataAdapter = TransactionsListAdapter()
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

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}