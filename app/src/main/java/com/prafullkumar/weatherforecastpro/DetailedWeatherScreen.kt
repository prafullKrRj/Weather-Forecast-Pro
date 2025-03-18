package com.prafullkumar.weatherforecastpro

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
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

@Composable
fun DetailedWeatherScreen(padding: PaddingValues) {
    Box(Modifier.padding(padding)) {
        WeatherScreen()
    }
}

@Composable
private fun WeatherScreen() {
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
                // Winter background
                Image(
                    painter = painterResource(id = R.drawable.snow),
                    contentDescription = "Winter landscape",
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
                        text = "25 January, Wednesday",
                        color = Color.White,
                        fontSize = 16.sp,
                        fontWeight = FontWeight.Medium
                    )

                    Spacer(modifier = Modifier.weight(1f))

                    // Temperature
                    Row(
                        verticalAlignment = Alignment.Bottom
                    ) {
                        Text(
                            text = "-10",
                            color = Color.White,
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
                        text = "Real feel -18°C",
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
                            text = "Irkutsk",
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
                    value = "5-8 km/h",
                    iconResId = R.drawable.ic_wind,
                    backgroundColor = Color(0xFF8B98FF),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Pressure
                WeatherParameterCard(
                    title = "Pressure",
                    value = "1000 MB",
                    iconResId = R.drawable.ic_pressure,
                    backgroundColor = Color(0xFF9B98FF),
                    modifier = Modifier.weight(1f)
                )

                Spacer(modifier = Modifier.width(8.dp))

                // Humidity
                WeatherParameterCard(
                    title = "Humidity",
                    value = "51%",
                    iconResId = R.drawable.ic_humidity,
                    backgroundColor = Color(0xFF66AFFF),
                    modifier = Modifier.weight(1f)
                )
            }

            Spacer(modifier = Modifier.height(16.dp))

            // Hourly forecast
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(120.dp)
                    .background(Color(0xFF1E1E1E), RoundedCornerShape(16.dp))
                    .padding(16.dp)
            ) {
                // Time labels
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    TimeLabel(time = "NOW", temp = "-10°", iconResId = R.drawable.ic_snow_cloud)
                    TimeLabel(time = "3 AM", temp = "-23°", iconResId = R.drawable.ic_snow_cloud)
                    TimeLabel(time = "4 AM", temp = "-22°", iconResId = R.drawable.ic_snow_cloud)
                    TimeLabel(time = "5 AM", temp = "-14°", iconResId = R.drawable.ic_snow_cloud)
                    TimeLabel(time = "6 AM", temp = "-3°", iconResId = R.drawable.ic_snow_cloud)
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
    time: String,
    temp: String,
    iconResId: Int
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = time,
            color = Color.White,
            fontSize = 12.sp
        )

        Spacer(modifier = Modifier.height(4.dp))

        Icon(
            painter = painterResource(id = iconResId),
            contentDescription = "Weather icon",
            tint = Color.White,
            modifier = Modifier.size(24.dp)
        )

        Spacer(modifier = Modifier.height(4.dp))

        Text(
            text = temp,
            color = Color.White,
            fontSize = 14.sp,
            fontWeight = FontWeight.Bold
        )
    }
}