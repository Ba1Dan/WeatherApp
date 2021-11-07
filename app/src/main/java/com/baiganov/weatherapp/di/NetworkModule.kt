package com.baiganov.weatherapp.di

import com.baiganov.weatherapp.BuildConfig
import com.baiganov.weatherapp.data.network.WeatherApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {

    @Singleton
    @Provides
    fun provideHttpClient() : OkHttpClient {
        return OkHttpClient.Builder()
            .readTimeout(15, TimeUnit.SECONDS)
            .connectTimeout(15, TimeUnit.SECONDS)
            .build()
    }

    @Singleton
    @Provides
    fun provideConverterFactory(): GsonConverterFactory {
        return GsonConverterFactory.create()
    }

    @Singleton
    @Provides
    fun provideRetrofitInstance(
        okHttpClient: OkHttpClient,
        gsonConverterFactory: GsonConverterFactory
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(gsonConverterFactory)
            .build()
    }

    @Singleton
    @Provides
    fun provideApiService(retrofit: Retrofit): WeatherApi {
        return retrofit.create(WeatherApi::class.java)
    }

    private const val BASE_URL = "http://api.openweathermap.org/data/2.5/"
    const val QUERY_API_KEY = "appid"
    const val QUERY_EXCLUDE = "exclude"
    const val QUERY_UNITS = "units"
    const val QUERY_LANG = "lang"
    const val QUERY_LAT = "lat"
    const val QUERY_LON = "lon"

    const val LAN = "ru"
    const val EXCLUDE = "hourly,minutely"
    const val UNITS = "metric"
    const val API_KEY = BuildConfig.WEATHER_API_KEY

}