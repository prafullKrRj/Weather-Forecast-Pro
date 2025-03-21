package com.prafullkumar.weatherforecastpro.app

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.prafullkumar.weatherforecastpro.R
import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.Weather
import com.prafullkumar.weatherforecastpro.data.dto.weatherResponse.WeatherResponse
import com.prafullkumar.weatherforecastpro.utils.Resource
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

@Composable
fun DetailedWeatherScreen(
    viewModel: HomeWeatherViewModel,
    padding: PaddingValues
) {
    val state by viewModel.state.collectAsState()
    when (state) {
        is Resource.Error -> {
            val error = (state as Resource.Error).exception.message.toString()
            Text(text = error)
        }

        Resource.Loading -> {
            // Loading indicator
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(padding),
                contentAlignment = Alignment.Center
            ) {
                CircularProgressIndicator()
            }
        }

        is Resource.Success<*> -> {
            val weatherResponse = (state as Resource.Success<WeatherResponse>).data
            Box(
                Modifier
                    .fillMaxSize()
                    .padding(padding)
            ) {
                WeatherScreen(weatherResponse)
            }
        }
    }
}

@Composable
private fun WeatherScreen(weatherResponse: WeatherResponse) {
    // Get the current weather data from the first item in the list
    val currentWeather = weatherResponse.list.firstOrNull()
    val currentCity = weatherResponse.city

    // Format date from dt
    val formattedDate = currentWeather?.dt?.let {
        SimpleDateFormat("EEEE, MMM dd", Locale.getDefault())
            .format(Date(it.toLong() * 1000))
    } ?: "N/A"

    // Helper function to get background image based on weather condition
    fun getBackgroundImageResource(weather: List<Weather>?): Int {
        return when (weather?.firstOrNull()?.main?.lowercase()) {
            "clear" -> R.drawable.clear
            "clouds" -> R.drawable.clouds
            "rain" -> R.drawable.rainy
            "snow" -> R.drawable.snow
            "thunderstorm" -> R.drawable.thunderstorm
            else -> R.drawable.clear
        }
    }

    // Helper function to get weather icon
    fun getWeatherIconResource(icon: String?): Int {
        return when (icon) {
            "01d" -> R.drawable.ic_clear_day
            "01n" -> R.drawable.ic_clear_night
            "02d", "03d" -> R.drawable.ic_partly_cloudy_day
            "02n", "03n" -> R.drawable.ic_partly_cloudy_night
            "04d", "04n" -> R.drawable.ic_cloudy
            "09d", "09n" -> R.drawable.ic_rain
            "10d" -> R.drawable.ic_rain_day
            "10n" -> R.drawable.ic_rain_night
            "11d" -> R.drawable.ic_thunderstorm_day
            "11n" -> R.drawable.ic_thunderstorm_night
            "13d", "13n" -> R.drawable.ic_snow
            "50d", "50n" -> R.drawable.ic_fog
            else -> R.drawable.ic_clear_day
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
                .padding(16.dp)
        ) {
            // Main weather display
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(450.dp)
                    .clip(RoundedCornerShape(24.dp))
            ) {
                // Weather background based on condition
                Image(
                    painter = painterResource(
                        id = getBackgroundImageResource(currentWeather?.weather)
                    ),
                    contentDescription = "Weather background",
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxSize()
                )

                // Gradient overlay
                Box(
                    modifier = Modifier
                        .fillMaxSize()
                        .background(
                            Brush.verticalGradient(
                                colors = listOf(
                                    Color(0x00000000),
                                    Color(0x40000000)
                                )
                            )
                        )
                )

                // Temperature and info
                Column(
                    modifier = Modifier
                        .fillMaxSize()
                        .padding(24.dp)
                ) {
                    // Date and day
                    Text(
                        text = formattedDate,
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Weather description
                    Text(
                        text = currentWeather?.weather?.firstOrNull()?.description?.capitalize()
                            ?: "N/A",
                        color = Color.White,
                        fontSize = 20.sp,
                        fontWeight = FontWeight.SemiBold
                    )

                    // Temperature
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = currentWeather?.main?.temp?.let { "${(it - 273.15).toInt()}" }
                                ?: "--", color = Color.White,
                            fontSize = 96.sp,
                            fontWeight = FontWeight.Bold,
                            lineHeight = 80.sp
                        )
                        Text(
                            text = "°C",
                            color = Color.White,
                            fontSize = 40.sp,
                            fontWeight = FontWeight.Medium,
                            modifier = Modifier.padding(bottom = 12.dp)
                        )
                    }

                    // Real feel
                    Text(
                        text = "Real feel ${currentWeather?.main?.feels_like?.let { (it - 273.15).toInt() }}°C",
                        color = Color.White,
                        fontSize = 14.sp,
                        fontWeight = FontWeight.Normal
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Location
                    Row(
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Text(
                            text = "${currentCity.name ?: "Unknown"}, ${currentCity.country ?: ""}",
                            color = Color.White,
                            fontSize = 14.sp,
                            fontWeight = FontWeight.Medium
                        )
                        Spacer(modifier = Modifier.width(4.dp))
                        Icon(
                            imageVector = Icons.Default.LocationOn,
                            contentDescription = "Location",
                            tint = Color.White,
                            modifier = Modifier.size(14.dp)
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Weather parameters
            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                // Wind
                WeatherParameterCard(
                    title = "Wind",
                    value = "${currentWeather?.wind?.speed ?: 0} m/s",
                    iconResId = R.drawable.ic_wind,
                    backgroundColor = Color(0xFF8B98FF),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Pressure
                WeatherParameterCard(
                    title = "Pressure",
                    value = "${currentWeather?.main?.pressure ?: 0} hPa",
                    iconResId = R.drawable.ic_pressure,
                    backgroundColor = Color(0xFF9B98FF),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Humidity
                WeatherParameterCard(
                    title = "Humidity",
                    value = "${currentWeather?.main?.humidity ?: 0}%",
                    iconResId = R.drawable.ic_humidity,
                    backgroundColor = Color(0xFF66AFFF),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))


            LazyRow(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp),
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                // Take 5 forecast items (each representing a 3-hour period)
                items(weatherResponse.list) { hourlyData ->
                    val time = SimpleDateFormat("HH:mm", Locale.getDefault())
                        .format(Date(hourlyData.dt.toLong() * 1000))
                    val temp = "${hourlyData.main.temp?.let { (it - 273.15).toInt() }}°"
                    val iconRes = getWeatherIconResource(hourlyData.weather.firstOrNull()?.icon)

                    TimeLabel(
                        time24 = time,
                        temp = temp,
                        iconResId = iconRes
                    )
                }
            }
        }
    }
}

@Composable
fun WeatherParameterCard(
    title: String,
    value: String,
    iconResId: Int,
    backgroundColor: Color,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(16.dp))
            .background(backgroundColor)
            .padding(16.dp)
    ) {
        Column(
            horizontalAlignment = Alignment.Start
        ) {
            Icon(
                painter = painterResource(id = iconResId),
                contentDescription = title,
                tint = Color.White,
                modifier = Modifier.size(24.dp)
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                text = title,
                color = Color.White,
                fontSize = 14.sp
            )

            Text(
                text = value,
                color = Color.White,
                fontSize = 14.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}
@Composable
fun TimeLabel(
    time24: String,
    temp: String,
    iconResId: Int
) {
    val time = SimpleDateFormat("h:mm a", Locale.getDefault())
        .format(Date(time24.toLong() * 1000))
    Card(
        modifier = Modifier
            .width(80.dp)
            .clip(RoundedCornerShape(16.dp))
    ) {
        Box(
            modifier = Modifier
                .background(
                    brush = Brush.verticalGradient(
                        colors = listOf(
                            Color(0xFF66AFFF),
                            Color(0xFF8B98FF)
                        )
                    )
                )
                .padding(12.dp)
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.fillMaxWidth()
            ) {
                Text(
                    text = time,
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium
                )

                Spacer(modifier = Modifier.height(8.dp))

                Icon(
                    painter = painterResource(id = iconResId),
                    contentDescription = "Weather icon",
                    tint = Color.White,
                    modifier = Modifier.size(28.dp)
                )

                Spacer(modifier = Modifier.height(8.dp))

                Text(
                    text = temp,
                    color = Color.White,
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}