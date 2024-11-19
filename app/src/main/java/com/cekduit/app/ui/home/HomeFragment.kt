package com.cekduit.app.ui.home

import android.graphics.Color
import android.os.Bundle
import android.view.ContextMenu
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
import com.cekduit.app.R
import com.cekduit.app.databinding.FragmentHomeBinding
import com.cekduit.app.testdir.DummyData
import com.cekduit.app.ui.components.CustomMarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.listener.OnChartValueSelectedListener
import com.github.mikephil.charting.model.GradientColor

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        val textView: TextView = binding.textHome
        homeViewModel.text.observe(viewLifecycleOwner) {
            textView.text = it
        }

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
            color = Color.BLUE
            lineWidth = 2f
            setCircleColor(Color.RED)
            circleRadius = 5f
            gradientColors = listOf(
                GradientColor(Color.BLUE, Color.GREEN),
            )
        }

        val XAxis = chart.xAxis
        XAxis.apply {
            setDrawAxisLine(false)
            setDrawGridLines(false)
        }

        val markerView = CustomMarkerView(requireContext(), R.layout.custom_marker_view)
        markerView.chartView = chart

        chart.apply {
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
            setScaleEnabled(false) // Disable scaling untuk interaksi yang lebih baik
            setPinchZoom(false)
            setDrawMarkers(true)

            // Penting: set maximum highlight distance
            maxHighlightDistance = 300f

            // Enable touch gestures
            setTouchEnabled(true)

            // Update chart setelah konfigurasi
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

        // Refresh chart


        return root

    }



    override fun onDestroyView() {
        super.onDestroyView()
        _binding = null
    }
}