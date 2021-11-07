package com.baiganov.weatherapp.bindingadapter

import android.util.Log
import android.widget.ImageView
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.baiganov.weatherapp.R
import com.baiganov.weatherapp.model.Weather
import com.bumptech.glide.Glide
import java.text.DateFormat
import java.text.SimpleDateFormat
import java.util.*

object ItemDayBindingAdapter {

    @BindingAdapter("loadImage")
    @JvmStatic
    fun loadImage(imageView: ImageView, image: String?) {
        if (image != null) {
            val url = "http://openweathermap.org/img/wn/$image@2x.png"
            Glide.with(imageView.context)
                .load(url)
                .centerCrop()
                .into(imageView)
        } else {
            Log.d(javaClass.simpleName, "image is null")
        }
    }

    @BindingAdapter("formatTemp")
    @JvmStatic
    fun formatTemp(textView: TextView, dateInMilliseconds: Long?) {

        if (dateInMilliseconds != null) {
            val dateFormat: DateFormat = SimpleDateFormat(DATE_PATTERN, Locale.getDefault())
            val date = dateFormat.format(Date(dateInMilliseconds * 1000))
            textView.text = date.replaceFirstChar { if (it.isLowerCase()) it.titlecase(Locale.getDefault()) else it.toString() }
        } else {
            textView.text = DEFAULT_VALUE
        }
    }

    @BindingAdapter("setTemp")
    @JvmStatic
    fun setTemp(textView: TextView, temperature: Double?) {

        if (temperature != null) {
            textView.text = textView.context.getString(R.string.format_celsius, temperature.toInt())
        } else {
            textView.text = DEFAULT_VALUE
        }
    }

    @BindingAdapter("setWind")
    @JvmStatic
    fun setWind(textView: TextView, wind: Double?) {

        if (wind != null) {
            textView.text = textView.context.getString(R.string.format_meter_sec, wind.toInt())
        } else {
            textView.text = DEFAULT_VALUE
        }
    }

    @BindingAdapter("setHumidity")
    @JvmStatic
    fun setHumidity(textView: TextView, humidity: Int?) {

        if (humidity != null) {
            textView.text = textView.context.getString(R.string.format_percent, humidity)
        } else {
            textView.text = DEFAULT_VALUE
        }
    }


    @BindingAdapter("setPressure")
    @JvmStatic
    fun setPressure(textView: TextView, pressure: Double?) {

        if (pressure != null) {
            textView.text = textView.context.getString(R.string.format_pressure, (pressure / PASCAL_VALUE).toInt())
        } else {
            textView.text = DEFAULT_VALUE
        }
    }

    private const val DEFAULT_VALUE = "-"
    private const val PASCAL_VALUE = 1.33317
    private const val DATE_PATTERN = "EEEE, d MMMM"
}