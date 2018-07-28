package com.example.hendratay.whatheweather.presentation.model

abstract class ListItem {

    companion object {
        val TYPE_DATE = 0
        val TYPE_GENERAL = 1
        val TYPE_FOOTER = 2
    }

    abstract fun getType(): Int

}