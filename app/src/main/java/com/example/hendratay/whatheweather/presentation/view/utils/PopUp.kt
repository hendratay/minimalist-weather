package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.graphics.Color
import android.widget.TextView

fun Toast(context: Context, message: CharSequence) {
    val toast = android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT)
    toast.view.apply {
        setBackgroundColor(Color.BLACK)
        setPadding(36, 36, 36, 36)
        findViewById<TextView>(android.R.id.message).textSize = 14F
    }
    toast.show()
}