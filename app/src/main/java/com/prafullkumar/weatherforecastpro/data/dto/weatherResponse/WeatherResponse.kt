package com.prafullkumar.weatherforecastpro.data.dto.weatherResponse
import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("city")
    val city: City,

    @SerializedName("cnt")
    val cnt: Int,

    @SerializedName("cod")
    val cod: String,

    @SerializedName("list")
    val list: List<Item>,

    @SerializedName("message")
    val message: Int?
)
data class Weather(
    @SerializedName("description")
    val description: String?,
    @SerializedName("icon")
    val icon: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("main")
    val main: String?
)
data class City(
    @SerializedName("coord")
    val coord: Coord?,
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("name")
    val name: String?,
    @SerializedName("population")
    val population: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("timezone")
    val timezone: Int?
)
data class Clouds(
    @SerializedName("all")
    val all: Int?
)
data class Coord(
    @SerializedName("lat")
    val lat: Double?,
    @SerializedName("lon")
    val lon: Double?
)
data class Item(
    @SerializedName("clouds")
    val clouds: Clouds,
    @SerializedName("dt")
    val dt: Int,
    @SerializedName("dt_txt")
    val dt_txt: String,
    @SerializedName("main")
    val main: Main,
    @SerializedName("pop")
    val pop: Double?, // Changed to Double? as probability of precipitation is typically a decimal value and can be null
    @SerializedName("sys")
    val sys: Sys,
    @SerializedName("visibility")
    val visibility: Int?,
    @SerializedName("weather")
    val weather: List<Weather>,
    @SerializedName("wind")
    val wind: Wind
)
data class Main(
    @SerializedName("feels_like")
    val feels_like: Double?,
    @SerializedName("grnd_level")
    val grnd_level: Int?,
    @SerializedName("humidity")
    val humidity: Int?,
    @SerializedName("pressure")
    val pressure: Int?,
    @SerializedName("sea_level")
    val sea_level: Int?,
    @SerializedName("temp")
    val temp: Double?,
    @SerializedName("temp_kf")
    val temp_kf: Double?,
    @SerializedName("temp_max")
    val temp_max: Double?,
    @SerializedName("temp_min")
    val temp_min: Double?
)
data class Sys(
    @SerializedName("pod")
    val pod: String?
)
data class Wind(
    @SerializedName("deg") val deg: Int?,
    @SerializedName("gust") val gust: Double?,
    @SerializedName("speed") val speed: Double?
)