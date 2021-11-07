package com.baiganov.weatherapp.adapters

import androidx.recyclerview.widget.DiffUtil
import com.baiganov.weatherapp.model.DailyForecast

class ContentDiffUtil : DiffUtil.ItemCallback<DailyForecast>() {


    override fun areItemsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return true
    }

    override fun areContentsTheSame(oldItem: DailyForecast, newItem: DailyForecast): Boolean {
        return false
    }
}