package com.cekduit.app.ui.components

import android.content.Context
import android.graphics.Canvas
import android.widget.TextView
import androidx.interpolator.view.animation.FastOutSlowInInterpolator
import com.cekduit.app.R
import com.github.mikephil.charting.components.MarkerView
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.highlight.Highlight
import com.github.mikephil.charting.utils.MPPointF

class CustomMarkerView(context: Context, layoutResource: Int) : MarkerView(context, layoutResource) {
    private val tvContent: TextView = findViewById(R.id.tvContent)
    private var isFirstDraw = true

    init {
        measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
    }

    override fun refreshContent(e: Entry?, highlight: Highlight?) {
        e?.let {
            tvContent.text = "Value: ${String.format("%.1f", it.y)}"
        }
        super.refreshContent(e, highlight)
    }

    override fun draw(canvas: Canvas?, posX: Float, posY: Float) {
        val point = getOffsetForDrawingAtPoint(posX, posY)
        val finalX = posX + point.x
        val finalY = posY + point.y

        if (isFirstDraw) {
            translationX = finalX
            translationY = finalY
            isFirstDraw = false
        } else {
            animate()
                .translationX(finalX)
                .translationY(finalY)
                .setDuration(200)
                .setInterpolator(FastOutSlowInInterpolator())
                .start()
        }

        canvas?.let {
            it.translate(finalX, finalY)
            draw(it)
            it.translate(-finalX, -finalY)
        }
    }

    override fun getOffset(): MPPointF {
        return MPPointF(-(width / 2f), -height.toFloat())
    }
}