package com.baiganov.weatherapp.adapters

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.weatherapp.R
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.util.format
import com.bumptech.glide.Glide

class ContentViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

    private val date: TextView = itemView.findViewById(R.id.dateTextView)
    private val selectedView: View = itemView.findViewById(R.id.selectedView)
    private val maxTemp: TextView = itemView.findViewById(R.id.maxTempTextView)
    private val minTemp: TextView = itemView.findViewById(R.id.minTempTextView)
    private val iconImageView: ImageView = itemView.findViewById(R.id.iconImageView)

    fun bind(
        model: DailyForecastEntity,
        position: Int,
        selectedIndex: Int,
        clickListener: ItemClickListener
    ) {
        date.text = format(model.dt)
        maxTemp.text = itemView.context.getString(R.string.format_celsius, model.temp.max?.toInt())
        minTemp.text = itemView.context.getString(R.string.format_celsius, model.temp.min?.toInt())
        if (position == selectedIndex) {
            selectedView.visibility = View.VISIBLE
        } else {
            selectedView.visibility = View.GONE
        }
        itemView.setOnClickListener {
            selectedView.visibility = View.VISIBLE
            clickListener.onItemClick(position)
        }

        val url = "http://openweathermap.org/img/wn/${model.weather.icon}@2x.png"
        Glide.with(iconImageView.context)
            .load(url)
            .centerCrop()
            .into(iconImageView)
    }
}