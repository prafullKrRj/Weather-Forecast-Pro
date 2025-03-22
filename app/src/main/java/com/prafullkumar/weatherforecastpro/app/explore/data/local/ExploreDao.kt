package com.prafullkumar.weatherforecastpro.app.explore.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ExploreDao {

    @Query("SELECT * FROM explore_locations ORDER BY lastUpdated DESC LIMIT 10")
    fun getRecentSearches(): Flow<List<ExploreEntity>>

    @Query("UPDATE explore_locations SET isFavorite = :favorite WHERE cityName = :cityName")
    suspend fun toggleFavorite(cityName: String, favorite: Boolean)

    @Insert
    suspend fun insertRecentSearch(exploreEntity: ExploreEntity)
}