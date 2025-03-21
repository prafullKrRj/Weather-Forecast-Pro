package com.prafullkumar.weatherforecastpro.data

import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.WeatherResponse
import com.prafullkumar.weatherforecastpro.domain.repository.WeatherRepository
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class WeatherRepositoryImpl : WeatherRepository, KoinComponent {

    private val weatherApiService: WeatherApiService by inject()

    override suspend fun getWeatherData(
        lat: Double,
        lon: Double
    ): Flow<Resource<WeatherResponse>> {
        return flow {
            emit(Resource.Loading)
            try {
                val response = weatherApiService.getWeatherData(lat, lon)
                if (response.isSuccessful) {
                    emit(Resource.Success(response.body()!!))
                } else {
                    emit(Resource.Error(Exception(response.message())))
                }
            } catch (e: Exception) {
                emit(Resource.Error(e))
            }
        }
    }
}