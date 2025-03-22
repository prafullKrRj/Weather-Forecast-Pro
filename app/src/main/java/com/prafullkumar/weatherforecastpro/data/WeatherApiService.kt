package com.prafullkumar.weatherforecastpro.data

import com.prafullkumar.weatherforecastpro.data.dto.cityResponse.CityResponse
import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.WeatherResponse
import com.prafullkumar.weatherforecastpro.utils.ApiKey
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface WeatherApiService {


    @GET("/data/2.5/forecast")
    suspend fun getWeatherData(
        @Query("lat") lat: Double,
        @Query("lon") lon: Double,
        @Query("appid") apiKey: String = ApiKey.API_KEY
    ): Response<WeatherResponse>

    @GET("/data/2.5/weather")
    suspend fun getCityResponse(
        @Query("q") cityName: String,
        @Query("appid") apiKey: String = ApiKey.API_KEY
    ): Response<CityResponse>
}