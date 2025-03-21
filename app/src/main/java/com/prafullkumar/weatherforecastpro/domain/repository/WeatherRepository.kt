package com.prafullkumar.weatherforecastpro.domain.repository

import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.WeatherResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.Flow

interface  WeatherRepository {

    suspend fun getWeatherData(lat: Double, lon: Double): Flow<Resource<WeatherResponse>>
}
