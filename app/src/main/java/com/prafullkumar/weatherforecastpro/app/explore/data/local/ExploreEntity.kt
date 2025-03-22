package com.prafullkumar.weatherforecastpro.app.explore.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "explore_locations")
data class ExploreEntity(
    @PrimaryKey val cityName: String,
    val countryCode: String,
    val latitude: Double,
    val longitude: Double,
    val lastUpdated: Long,
    val isFavorite: Boolean = false
)
