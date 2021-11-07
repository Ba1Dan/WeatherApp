package com.baiganov.weatherapp.viewmodels

import android.app.Application
import android.content.Context
import android.location.Address
import android.location.Geocoder
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.baiganov.weatherapp.data.Repository
import com.baiganov.weatherapp.data.database.DailyForecastEntity
import com.baiganov.weatherapp.model.Result
import com.baiganov.weatherapp.util.NetworkResult
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import retrofit2.Response
import java.util.*
import javax.inject.Inject

@HiltViewModel
class MainViewModel @Inject constructor(
    private val repository: Repository,
    application: Application
) : AndroidViewModel(application) {

    private val _weatherForecasts: MutableLiveData<NetworkResult<List<DailyForecastEntity>>> =
        MutableLiveData()
    val weatherForecasts: LiveData<NetworkResult<List<DailyForecastEntity>>>
        get() = _weatherForecasts

    fun getData(latitude: Double, longitude: Double, cityName: String) = viewModelScope.launch(Dispatchers.IO) {
        getDataSafeCall(latitude = latitude, longitude = longitude, cityName = cityName)
    }

    fun loadDataFromDatabase() = viewModelScope.launch {
        val list = repository.local.getWeather()
        _weatherForecasts.postValue(NetworkResult.Success(list))
    }

    private suspend fun getDataSafeCall(latitude: Double, longitude: Double, cityName: String) {
        if (hasInternetConnection()) {
            try {

                val response =
                    repository.remote.getWeatherForecast(latitude = latitude, longitude = longitude)
                val result = handleWeatherForecastsResponse(response, cityName)
                _weatherForecasts.postValue(result)
                offlineCacheResult(result.data)
            } catch (e: Exception) {
                Log.d(javaClass.simpleName, e.message.toString())
                _weatherForecasts.value = NetworkResult.Error(message = "Not found")
            }
        } else {
            _weatherForecasts.postValue(NetworkResult.Error(message = "No Network Connection"))
        }
    }

    private fun offlineCacheResult(data: List<DailyForecastEntity>?) = viewModelScope.launch(Dispatchers.IO) {
        if (data != null) {
            repository.local.deleteAll()
            repository.local.insertData(data)
        }
    }

    private fun handleWeatherForecastsResponse(response: Response<Result>, cityName: String): NetworkResult<List<DailyForecastEntity>> {
        when {
            response.message().toString().contains("timeout") -> {
                return NetworkResult.Error(message = "Timeout")
            }
            response.code() == 402 -> {
                return NetworkResult.Error(message = "API Key Limited.")
            }
            response.body() == null -> {
                return NetworkResult.Error(message = "Recipes not found.")
            }
            response.isSuccessful -> {
                val result: Result = response.body()!!
                val list = result.dailyForecasts
                return NetworkResult.Success(list.map {
                    DailyForecastEntity(
                        dt = it.dt,
                        temp = it.temp,
                        weather = it.weather[0],
                        pressure = it.pressure,
                        windSpeed = it.wind_speed,
                        humidity = it.humidity,
                        feelsLike = it.feels_like,
                        cityName = cityName
                    )
                })
            }
            else -> {
                return NetworkResult.Error(message = response.message())
            }
        }
    }

    private fun hasInternetConnection(): Boolean {
        val connectivityManager = getApplication<Application>().getSystemService(
            Context.CONNECTIVITY_SERVICE
        ) as ConnectivityManager
        val activeNetwork = connectivityManager.activeNetwork ?: return false
        val capabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
        return when {
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
            capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
            else -> false
        }
    }
}