package com.cekduit.app.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.cekduit.app.R

@SuppressLint("DiscouragedApi")
fun getColorResourceByName(context: Context, categoryName: String): Int {
    val resourceName = "color_${categoryName.lowercase()}"
    val resourceId = context.resources.getIdentifier(resourceName, "color", context.packageName)
    return if (resourceId != 0) {
        ContextCompat.getColor(context, resourceId)
    } else {
        ContextCompat.getColor(context, R.color.color_others)
    }
}

fun Int.toColorStateList(): ColorStateList {
    return ColorStateList.valueOf(this)
}