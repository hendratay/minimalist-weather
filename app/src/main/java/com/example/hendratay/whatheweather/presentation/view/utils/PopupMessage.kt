package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.view.View

fun toast(context: Context, message: CharSequence) {
    val toast = android.widget.Toast.makeText(context, message, android.widget.Toast.LENGTH_SHORT)
    toast.view.apply {
        setBackgroundColor(Color.BLACK)
        setPadding(36, 36, 36, 36)
        findViewById<TextView>(android.R.id.message).textSize = 14F
    }
    toast.show()
}

fun snackBar(view: View, message: CharSequence, action: CharSequence, listener: (View) -> Unit) {
    val snackBar = Snackbar.make(view, message, Snackbar.LENGTH_SHORT)
    snackBar.view.apply {
        setBackgroundColor(Color.BLACK)
        findViewById<TextView>(android.support.design.R.id.snackbar_text).setTextColor(Color.LTGRAY)
    }
    snackBar.setAction(action, listener)
    snackBar.setActionTextColor(Color.WHITE)
    snackBar.show()
}