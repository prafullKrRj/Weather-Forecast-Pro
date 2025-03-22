package com.prafullkumar.weatherforecastpro.app.explore.data

import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreDao
import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreEntity
import com.prafullkumar.weatherforecastpro.app.explore.domain.repository.ExploreRepository
import com.prafullkumar.weatherforecastpro.data.WeatherApiService
import com.prafullkumar.weatherforecastpro.data.dto.cityResponse.CityResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow

class ExploreRepositoryImpl(
    private val exploreDao: ExploreDao, private val exploreApi: WeatherApiService
) : ExploreRepository {
    override fun getRecentSearches(): Flow<List<ExploreEntity>> = exploreDao.getRecentSearches()

    override suspend fun toggleFavorite(entity: ExploreEntity, favorite: Boolean) {
        exploreDao.toggleFavorite(entity.cityName, favorite)
    }

    override fun search(query: String): Flow<Resource<CityResponse>> {
        return flow {
            try {
                val response = exploreApi.getCityResponse(query)
                if (response.isSuccessful) {
                    coroutineScope {
                        exploreDao.insertRecentSearch(
                            ExploreEntity(
                                cityName = query,
                                lastUpdated = System.currentTimeMillis(),
                                isFavorite = false,
                                countryCode = response.body()!!.sys.country,
                                latitude = response.body()!!.coord.lat,
                                longitude = response.body()!!.coord.lon
                            )
                        )
                    }
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