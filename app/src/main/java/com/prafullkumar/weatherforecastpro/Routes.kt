package com.prafullkumar.weatherforecastpro

import kotlinx.serialization.Serializable

sealed interface Routes {

    @Serializable
    data object Splash : Routes

    @Serializable
    data object DetailedWeather : Routes

    @Serializable
    data object LocationSearch : Routes

    @Serializable
    data object SettingsScreen : Routes

}