package com.prafullkumar.weatherforecastpro.app.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.weatherforecastpro.app.explore.domain.repository.ExploreRepository
import com.prafullkumar.weatherforecastpro.data.dto.cityResponse.CityResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class SearchViewModel(
    private val query: String
) : ViewModel(), KoinComponent {

    private val repository by inject<ExploreRepository>()

    init {
        search()
    }

    private val _searchResult = MutableStateFlow<Resource<CityResponse>>(Resource.Loading)
    val searchResult = _searchResult.asStateFlow()

    fun search() {
        viewModelScope.launch {
            repository.search(query).collectLatest { response ->
                _searchResult.update {
                    response
                }
            }
        }
    }

}