package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.graphics.Color
import android.widget.TextView
import android.support.design.widget.Snackbar
import android.view.Gravity
import android.view.View
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast

fun toast(context: Context, message: String) {
    val yOffset = (60 * context.resources.displayMetrics.density).toInt()
    SuperActivityToast.create(context, Style(), Style.TYPE_STANDARD)
            .setColor(Color.BLACK)
            .setFrame(Style.FRAME_STANDARD)
            .setGravity(Gravity.END, -8, yOffset)
            .setText(message)
            .setTextColor(Color.WHITE)
            .setAnimations(Style.ANIMATIONS_POP)
            .show()
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