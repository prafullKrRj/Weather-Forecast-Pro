package com.prafullkumar.weatherforecastpro

import android.app.Application
import com.prafullkumar.weatherforecastpro.app.HomeWeatherViewModel
import com.prafullkumar.weatherforecastpro.data.WeatherApiService
import com.prafullkumar.weatherforecastpro.data.WeatherRepositoryImpl
import com.prafullkumar.weatherforecastpro.domain.repository.WeatherRepository
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.core.context.startKoin
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class WeatherForecastPro : Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@WeatherForecastPro)
            androidLogger()
            modules(module {
                single<WeatherApiService> {
                    Retrofit.Builder()
                        .baseUrl("https://api.openweathermap.org")
                        .addConverterFactory(GsonConverterFactory.create())
                        .build()
                        .create(WeatherApiService::class.java)
                }
                single<WeatherRepository> {
                    WeatherRepositoryImpl()
                }
                viewModel { HomeWeatherViewModel(get()) }
            })
        }
    }
}