package com.prafullkumar.weatherforecastpro.app.explore

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreEntity
import com.prafullkumar.weatherforecastpro.app.explore.domain.repository.ExploreRepository
import com.prafullkumar.weatherforecastpro.data.dto.cityResponse.CityResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.core.component.KoinComponent
import org.koin.core.component.inject

class ExploreViewModel : ViewModel(), KoinComponent {

    fun toggleFavorite(entity: ExploreEntity, favorite: Boolean) {
        viewModelScope.launch {
            repository.toggleFavorite(entity, favorite)
        }
    }

    private val repository: ExploreRepository by inject()

    val recentSearches = repository.getRecentSearches()
        .stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())


}