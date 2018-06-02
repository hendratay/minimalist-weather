package com.example.hendratay.whatheweather.presentation.model

class DateItem(val date: String): ListItem() {

    override fun getType(): Int {
        return TYPE_DATE
    }

}