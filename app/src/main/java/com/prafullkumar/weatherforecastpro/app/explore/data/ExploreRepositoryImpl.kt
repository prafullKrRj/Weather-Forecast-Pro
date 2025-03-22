package com.prafullkumar.weatherforecastpro.app.explore.data

import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreDao
import com.prafullkumar.weatherforecastpro.app.explore.domain.repository.ExploreRepository
import com.prafullkumar.weatherforecastpro.data.WeatherApiService

class ExploreRepositoryImpl(
    private val exploreDao: ExploreDao,
    private val exploreApi: WeatherApiService
): ExploreRepository {

}