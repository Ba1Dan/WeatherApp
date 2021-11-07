package com.baiganov.weatherapp.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.RecyclerView
import com.baiganov.weatherapp.R
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.model.DailyForecast

class ContentAdapter(private val clickListener: ItemClickListener) :
    RecyclerView.Adapter<ContentViewHolder>() {

    private var dataList = emptyList<DailyForecastEntity>()
    private var selectIndex = 0

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ContentViewHolder {
        return ContentViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_day, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ContentViewHolder, position: Int) {
        holder.bind(dataList[position], position, selectIndex, clickListener)
    }

    override fun getItemCount(): Int {
        return dataList.size
    }

    //TODO DiffUtil
    fun setData(newList: List<DailyForecastEntity>) {
        dataList = newList
        notifyDataSetChanged()
        selectIndex = 0
    }

    fun setSelectedIndex(position: Int) {
        selectIndex = position
        notifyItemChanged(position)
        notifyItemChanged(position - 1)
        notifyItemChanged(position + 1)
    }


}

fun interface ItemClickListener {
    fun onItemClick(position: Int)
}