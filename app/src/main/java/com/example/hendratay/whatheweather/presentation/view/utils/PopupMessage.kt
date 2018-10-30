package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import android.widget.Toast

fun toast(context: Context, message: CharSequence) {
    val unicodeIcon = String(Character.toChars(0x26A1))
    val toast = Toast.makeText(context, "$unicodeIcon    ️$message   $unicodeIcon",
            android.widget.Toast.LENGTH_SHORT)
    toast.view.apply {
        setBackgroundColor(Color.BLACK)
        findViewById<TextView>(android.R.id.message).textSize = 14F
        findViewById<TextView>(android.R.id.message).setTextColor(Color.WHITE)
    }
    val xOffset = (56 * context.resources.displayMetrics.density).toInt()
    toast.setGravity(Gravity.TOP, 0, xOffset)
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