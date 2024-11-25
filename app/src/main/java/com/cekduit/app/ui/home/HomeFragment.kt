package com.cekduit.app.ui.home

import android.annotation.SuppressLint
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
import com.github.mikephil.charting.utils.Utils

class HomeFragment : Fragment() {

    private var _binding: FragmentHomeBinding? = null

    // This property is only valid between onCreateView and
    // onDestroyView.
    private val binding get() = _binding!!

    @SuppressLint("UseCompatLoadingForDrawables")
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val homeViewModel =
            ViewModelProvider(this).get(HomeViewModel::class.java)

        _binding = FragmentHomeBinding.inflate(inflater, container, false)
        val root: View = binding.root

        homeViewModel.text.observe(viewLifecycleOwner) {
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
            color = requireContext().getColor(R.color.blue_primary, )
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