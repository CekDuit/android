package com.cekduit.app.ui.createTransaction

import android.app.Dialog
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ArrayAdapter
import com.cekduit.app.R
import com.cekduit.app.databinding.FragmentCreateTransactionBinding
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.google.android.material.datepicker.CalendarConstraints
import com.google.android.material.datepicker.DateValidatorPointBackward
import com.google.android.material.datepicker.MaterialDatePicker
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit

class AddTransactionBottomSheetDialogFragment: BottomSheetDialogFragment() {
    private var _binding: FragmentCreateTransactionBinding? = null
    private val binding get() = _binding!!

    private lateinit var datePicker: MaterialDatePicker<Long>

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentCreateTransactionBinding.inflate(inflater, container, false)

        val root = binding.root

        initiateDatePicker()
        binding.dateField.isFocusable = false
        binding.dateField.setOnClickListener {
            if (!datePicker.isAdded) {
                datePicker.show(childFragmentManager, "DATE_PICKER")
            }
        }

        return root
    }

    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val bottomSheetDialog = super.onCreateDialog(savedInstanceState) as BottomSheetDialog
        bottomSheetDialog.setCancelable(false)
        bottomSheetDialog.setCanceledOnTouchOutside(false)
        return bottomSheetDialog
    }

    private fun initiateDatePicker() {
        val currentDate = Calendar.getInstance().timeInMillis
        val tenDaysAgo = currentDate - TimeUnit.DAYS.toMillis(10)

        // set initial date to current date
        binding.dateField.setText(SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
            .format(Date(currentDate)))

        val constraints = CalendarConstraints.Builder()
            .setStart(tenDaysAgo) // 10 hari ke belakang
            .setEnd(currentDate) // Hari ini sebagai batas akhir
            .setValidator(DateValidatorPointBackward.now())


        datePicker = MaterialDatePicker.Builder.datePicker()
            .setTitleText(getString(R.string.select_date))
            .setCalendarConstraints(constraints.build())
            .build()

        datePicker.addOnPositiveButtonClickListener { selection ->
            val selectedDate = SimpleDateFormat("dd/MM/yyyy", Locale.getDefault())
                .format(Date(selection))

            binding.dateField.setText(selectedDate)
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}