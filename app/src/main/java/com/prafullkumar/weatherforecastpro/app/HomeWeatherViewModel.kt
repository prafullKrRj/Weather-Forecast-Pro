package com.prafullkumar.weatherforecastpro.app

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.WeatherResponse
import com.prafullkumar.weatherforecastpro.domain.repository.WeatherRepository
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.androidx.compose.get

class HomeWeatherViewModel(private val repository: WeatherRepository) : ViewModel() {

    private val _state = MutableStateFlow<Resource<WeatherResponse>>(Resource.Loading)
    val state = _state.asStateFlow()


    init {
        getWeatherData()
    }

    fun getWeatherData() {
        viewModelScope.launch {
            repository.getWeatherData(18.60716156420515, 73.87949480939177).collectLatest { response ->
                _state.update {
                    response
                }
            }
        }
    }
}
