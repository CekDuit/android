package com.cekduit.app.ui.home

import android.annotation.SuppressLint
import android.graphics.Color
import android.os.Bundle
import android.util.Log
import android.view.ContextMenu
import android.view.LayoutInflater
import android.view.Menu
import android.view.MenuInflater
import android.view.MenuItem
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.core.content.res.ResourcesCompat
import androidx.core.view.MenuProvider
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import com.bumptech.glide.load.engine.Resource
import com.cekduit.app.R
import com.cekduit.app.databinding.FragmentHomeBinding
import com.cekduit.app.testdir.DummyData
import com.cekduit.app.testdir.TransactionDummy
import com.cekduit.app.ui.adapter.TransactionItemAdapter
import com.cekduit.app.ui.components.CustomMarkerView
import com.cekduit.app.utils.ViewModelFactory
import com.cekduit.app.utils.getColorResourceByName
import com.github.mikephil.charting.components.Legend
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.data.PieData
import com.github.mikephil.charting.data.PieDataSet
import com.github.mikephil.charting.data.PieEntry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.model.GradientColor
import com.github.mikephil.charting.utils.Utils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null
    private val binding get() = _binding!!

    private val viewModel: HomeViewModel by viewModels {
        ViewModelFactory.getInstance(requireActivity())
    }

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        viewModel.getDisplayName().observe(viewLifecycleOwner, { displayName ->
            binding.tvGreeting.text = "Hello, $displayName"
        })

//        val accountName = arguments?.getString("ACCOUNT_NAME")
//        val accountEmail = arguments?.getString("ACCOUNT_EMAIL")
//        val tokenId = arguments?.getString("TOKEN_ID")
//
//        if (accountName != null) {
//            binding.tvGreeting.text = "Hello, $accountName"
//        }
//
//        if (tokenId != null) {
//            // Example: Log the token ID or use it as needed
//            Log.d("HomeFragment", "Token ID: $tokenId")
//        }
//
//        if (accountEmail != null) {
//            // Example: Log the token ID or use it as needed
//            Log.d("HomeFragment", "Email: $accountEmail")
//        }


        val chart = binding.chart
        // Dummy data
        val dataObjects = listOf(
            DummyData(1f, 10f),
            DummyData(2f, 20f),
            DummyData(3f, 15f),
            DummyData(4f, 30f),
            DummyData(5f, 25f)
        )

        // Konversi data dummy ke Entry
        val entries = dataObjects.map { data ->
            Entry(data.valueX, data.valueY)
        }

        // Buat LineDataSet dan atur properti styling
        val dataSet = LineDataSet(entries, "Sample Data").apply {
            mode = LineDataSet.Mode.CUBIC_BEZIER
            color = requireContext().getColor(R.color.blue_primary)
            lineWidth = 2f
            setDrawCircles(false)
            setDrawFilled(true)
            setDrawFilled(true)
            setDrawValues(false)
            lineWidth = 2f
            fillDrawable = if (Utils.getSDKInt() >= 18) {
                resources.getDrawable(R.drawable.line_chart_gradient, null)
            } else {
                null
            }
        }

        val XAxis = chart.xAxis
        XAxis.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }

        val markerView = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        markerView.chartView = chart

        chart.apply {
            minOffset = 0f
            data = LineData(dataSet)
            description.isEnabled = false
            legend.isEnabled = false
            axisLeft.isEnabled = false
            axisRight.isEnabled = false
            xAxis.isEnabled = false
            marker = markerView

            // Pengaturan tambahan untuk interaksi yang lebih baik
            isHighlightPerDragEnabled = true
            isHighlightPerTapEnabled = true
            isDragEnabled = true
            setScaleEnabled(false)
            setPinchZoom(false)
            setDrawMarkers(true)

            maxHighlightDistance = 300f

            // Enable touch gestures
            setTouchEnabled(true)

            invalidate()
        }

        val pieChartData = arrayOf(10f, 20f, 30f, 40f)
        val pieChartLabel = arrayOf("Food", "Drinks", "Transportation", "Others")
        val pieEntries = pieChartData.mapIndexed { index, value ->
            PieEntry(value, pieChartLabel[index])
        }

        val pieDataSet = PieDataSet(pieEntries, "").apply {
            colors = pieEntries.map { entry ->
                getColorResourceByName(requireContext(), entry.label)
            }
            valueTextSize = 12f
            valueTextColor = Color.BLACK
            setDrawValues(false)
        }

        binding.pieChart.apply {
            data = PieData(pieDataSet)
            description.isEnabled = false
            setTransparentCircleAlpha(0)
            setHoleColor(Color.TRANSPARENT)
            holeRadius = 70f
            description.isEnabled = false
            setDrawEntryLabels(false)

            legend.apply {
                horizontalAlignment = Legend.LegendHorizontalAlignment.RIGHT
                verticalAlignment = Legend.LegendVerticalAlignment.CENTER
                orientation = Legend.LegendOrientation.VERTICAL
                textColor = ResourcesCompat.getColor(resources, R.color.md_theme_onSurface, null)
            }
            invalidate()
        }

        // Listener untuk menangkap klik dan memulai animasi perpindahan
        chart.setOnChartValueSelectedListener(object : OnChartValueSelectedListener {
            override fun onValueSelected(e: Entry?, h: Highlight?) {

            }

            override fun onNothingSelected() {
                // Opsional: Reset atau sembunyikan marker jika diperlukan
            }
        })

        setupRecentTransaction()


        return root

    }

    private fun setupRecentTransaction() {
        binding.rvRecentTransaction.apply {
            layoutManager = LinearLayoutManager(requireContext())
            val dummyData = TransactionDummy().dummyData.transactions
            val dataAdapter = TransactionItemAdapter(
                onItemClicked = {},
                onItemHold = {}
            )
            dataAdapter.setData(dummyData)
            adapter = dataAdapter
        }
    }

    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}