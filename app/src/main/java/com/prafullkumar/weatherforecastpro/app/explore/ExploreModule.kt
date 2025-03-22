package com.prafullkumar.weatherforecastpro.app.explore

import androidx.room.Room
import com.prafullkumar.weatherforecastpro.app.explore.data.ExploreRepositoryImpl
import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreDao
import com.prafullkumar.weatherforecastpro.app.explore.data.local.ExploreDatabase
import com.prafullkumar.weatherforecastpro.app.explore.domain.repository.ExploreRepository
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module

val exploreModule = module {
    viewModel { ExploreViewModel() }

    single<ExploreDatabase> {
        Room.databaseBuilder(
            androidContext(),
            ExploreDatabase::class.java,
            "explore_database"
        ).build()
    }
    single<ExploreDao> {
        get<ExploreDatabase>().exploreDao()
    }
    single<ExploreRepository> {
        ExploreRepositoryImpl(get(), get())
    }
}