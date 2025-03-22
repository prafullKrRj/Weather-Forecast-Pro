package com.prafullkumar.weatherforecastpro.app.explore.data.local

import androidx.room.Database
import androidx.room.RoomDatabase

@Database(
    entities = [
        ExploreEntity::class
    ], version = 1, exportSchema = false
)
abstract class ExploreDatabase : RoomDatabase() {
    abstract fun exploreDao(): ExploreDao
}