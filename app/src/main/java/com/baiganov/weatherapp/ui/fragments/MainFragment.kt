package com.baiganov.weatherapp.ui.fragments

import android.os.Bundle
import android.util.Log
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.baiganov.weatherapp.R
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.databinding.FragmentMainBinding
import com.baiganov.weatherapp.model.DailyForecast


class MainFragment : Fragment() {

    private var _binding: FragmentMainBinding? = null
    private val binding get() = _binding!!

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        _binding = FragmentMainBinding.inflate(inflater, container, false)
        arguments?.let {
            val model: DailyForecastEntity? = it.getParcelable(ARG_MODEL)
            Log.d("PagerAdapter", "str: $model")
            if (model != null) {
                binding.model = model
            }
        }
        return binding.root
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    companion object {

        private const val ARG_MODEL = "arg_model"

        fun newInstance(model: DailyForecastEntity): Fragment {
            return MainFragment().apply {
                arguments = Bundle().apply {
                    putParcelable(ARG_MODEL, model)
                }
            }
        }
    }
}