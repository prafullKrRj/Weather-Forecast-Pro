package com.prafullkumar.weatherforecastpro.app.explore.domain.repository

import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreEntity
import com.prafullkumar.weatherforecastpro.data.dto.cityResponse.CityResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.flow.Flow

interface ExploreRepository {
    fun getRecentSearches(): Flow<List<ExploreEntity>>

    suspend fun toggleFavorite(entity: ExploreEntity, favorite: Boolean)


    fun search(query: String): Flow<Resource<CityResponse>>

}
