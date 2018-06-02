package com.example.hendratay.whatheweather.presentation.model

abstract class ListItem {

    companion object {
        val TYPE_DATE = 0
        val TYPE_GENERAL = 1
    }

    abstract fun getType(): Int

}