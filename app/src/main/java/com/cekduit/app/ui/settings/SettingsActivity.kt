package com.cekduit.app.ui.settings

import android.os.Bundle
import androidx.activity.enableEdgeToEdge
import androidx.appcompat.app.AppCompatActivity
import androidx.core.view.ViewCompat
import androidx.core.view.WindowInsetsCompat
import com.cekduit.app.R
import com.cekduit.app.databinding.ActivitySettingsBinding
import com.google.android.material.color.MaterialColors
import com.google.android.material.shape.MaterialShapeDrawable

class SettingsActivity : AppCompatActivity() {
    private var _binding: ActivitySettingsBinding? = null
    private val binding get() = _binding!!

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        _binding = ActivitySettingsBinding.inflate(layoutInflater)
        enableEdgeToEdge()
        setContentView(binding.root)

        binding.appBarLayout.setStatusBarForegroundColor(
            MaterialColors.getColor(binding.appBarLayout, com.google.android.material.R.attr.colorSurface))

        val toolbar = binding.toolbar
        setSupportActionBar(toolbar)

        supportActionBar?.setDisplayHomeAsUpEnabled(true)
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}