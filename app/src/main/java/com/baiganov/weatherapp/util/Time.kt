package com.baiganov.weatherapp.util

import android.widget.TextView
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

fun format(dateInMilliseconds: Long): String {
    val dateFormat: DateFormat = SimpleDateFormat("E, dd", Locale.getDefault())
    return dateFormat.format(Date(dateInMilliseconds * 1000))
}