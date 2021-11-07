package com.baiganov.weatherapp.adapters

import android.util.Log
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.viewpager2.adapter.FragmentStateAdapter
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.model.DailyForecast
import com.baiganov.weatherapp.ui.fragments.MainFragment

class PagerAdapter(
    private val data: List<DailyForecastEntity>,
    fragmentActivity: FragmentActivity
) : FragmentStateAdapter(fragmentActivity){


    override fun getItemCount(): Int {
        return data.size
    }

    override fun createFragment(position: Int): Fragment {
        Log.d(javaClass.simpleName, "create ${data[position]}")
        return  MainFragment.newInstance(data[position])
    }
}