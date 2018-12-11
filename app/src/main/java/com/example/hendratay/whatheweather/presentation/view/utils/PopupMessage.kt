package com.example.hendratay.whatheweather.presentation.view.utils

import android.content.Context
import android.graphics.Color
import android.graphics.Typeface
import android.view.Gravity
import com.github.johnpersano.supertoasts.library.Style
import com.github.johnpersano.supertoasts.library.SuperActivityToast

fun toast(context: Context, message: String) {
    val yOffset = (60 * context.resources.displayMetrics.density).toInt()
    SuperActivityToast.create(context, Style(), Style.TYPE_STANDARD)
            .setTouchToDismiss(true)
            .setColor(Color.BLACK)
            .setFrame(Style.FRAME_STANDARD)
            .setGravity(Gravity.END, -8, yOffset)
            .setText(message)
            .setTextColor(Color.WHITE)
            .setAnimations(Style.ANIMATIONS_POP)
            .show()
}

fun snackBar(context: Context, message: String, action: String, listener: SuperActivityToast.OnButtonClickListener) {
    SuperActivityToast.create(context, Style(), Style.TYPE_BUTTON)
            .setTouchToDismiss(true)
            .setButtonText(action)
            .setButtonTextColor(Color.WHITE)
            .setButtonTypefaceStyle(Typeface.NORMAL)
            .setOnButtonClickListener(action, null, listener)
            .setColor(Color.BLACK)
            .setText(message)
            .setTextColor(Color.LTGRAY)
            .setAnimations(Style.ANIMATIONS_FLY)
            .show()
}