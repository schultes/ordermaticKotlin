package com.example.ordermatic.view.helper

import android.content.Context
import android.widget.ImageView
import kotlin.math.roundToInt

fun ImageView.setImageFromMipmap(mipmapName: Int) {
    setImageResource(mipmapName)
}

fun ImageView.setImageFromMipmap(context: Context, mipmapName: Int, width: Float, height: Float) {
    setImageResource(mipmapName)
    val density = context.resources.displayMetrics.density
    layoutParams.height = (height * density).roundToInt()
    layoutParams.width = (width * density).roundToInt()
}

