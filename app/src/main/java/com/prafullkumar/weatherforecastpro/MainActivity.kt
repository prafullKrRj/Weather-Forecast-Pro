package com.prafullkumar.weatherforecastpro

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.vectorResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.permissions.*
import com.prafullkumar.weatherforecastpro.ui.theme.WeatherForecastProTheme

class MainActivity : ComponentActivity() {
    private val LOCATION_PERMISSION_REQUEST_CODE = 1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            WeatherForecastProTheme {
                LocationPermissionScreen()
            }
        }
    }
}


@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun LocationPermissionScreen() {
    val context = LocalContext.current
    val permissionState = rememberPermissionState(
        android.Manifest.permission.ACCESS_FINE_LOCATION
    )

    LaunchedEffect(Unit) {
        if (!permissionState.status.isGranted &&
            !permissionState.status.shouldShowRationale
        ) {
            permissionState.launchPermissionRequest()
        }
    }

    when {
        permissionState.status.isGranted -> {
            Surface(Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.surface) {
                App()
            }
        }

        permissionState.status.shouldShowRationale -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Permissions are necessary for showing weather updates.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Request Permission")
                }
            }
        }

        else -> {
            Column(
                Modifier
                    .fillMaxSize()
                    .padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text(
                    text = "Location permission is required for this app to function properly.",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Medium,
                    modifier = Modifier.padding(16.dp)
                )
                Button(onClick = { permissionState.launchPermissionRequest() }) {
                    Text("Grant Permission")
                }
            }
        }
    }
}

@Composable
fun App() {
    val navController = rememberNavController()
    NavHost(
        navController,
        startDestination = Routes.DetailedWeather,
        modifier = Modifier.fillMaxSize()
    ) {
        composable<Routes.DetailedWeather> {
            MainScreen(navController, Routes.DetailedWeather)
        }
        composable<Routes.LocationSearch> {
            MainScreen(navController, Routes.LocationSearch)
        }
        composable<Routes.SettingsScreen> {
            MainScreen(navController, Routes.SettingsScreen)
        }
    }
}

@Composable
fun MainScreen(
    navController: NavController,
    currentRoute: Routes
) {
    Scaffold(Modifier.fillMaxSize(), bottomBar = {
        BottomNavigationBar(currentRoute) {
            navController.navigate(it)
        }
    }) { innerPadding ->
        when (currentRoute) {
            Routes.DetailedWeather -> DetailedWeatherScreen(innerPadding)
            Routes.LocationSearch -> LocationSearchScreen(innerPadding)
            Routes.SettingsScreen -> SettingsScreen(innerPadding)
            Routes.Splash -> {

            }
        }
    }
}

@Composable
fun SettingsScreen(padding: PaddingValues) {

}

@Composable
fun LocationSearchScreen(padding: PaddingValues) {

}


@Composable
private fun BottomNavigationBar(
    currentRoute: Routes,
    onRouteSelected: (Routes) -> Unit
) {
    NavigationBar(
        modifier = Modifier
            .clip(RoundedCornerShape(topStart = 20.dp, topEnd = 20.dp))
            .shadow(elevation = 8.dp),
        containerColor = MaterialTheme.colorScheme.surfaceContainer,
        tonalElevation = 8.dp
    ) {
        val items = listOf(
            Triple(
                Icons.Default.Home,
                "Home",
                Routes.DetailedWeather
            ),
            Triple(
                ImageVector.vectorResource(R.drawable.round_explore_24),
                "Search",
                Routes.LocationSearch
            ),
            Triple(
                Icons.Default.Settings,
                "Settings",
                Routes.SettingsScreen
            )
        )

        items.forEach { (icon, description, route) ->
            val selected = currentRoute == route
            NavigationBarItem(
                icon = {
                    Icon(
                        imageVector = icon,
                        contentDescription = description,
                        modifier = Modifier.size(26.dp)
                    )
                },
                label = {
                    Text(
                        text = description,
                        style = MaterialTheme.typography.labelSmall,
                        fontWeight = if (selected) FontWeight.Bold else FontWeight.Normal
                    )
                },
                selected = selected,
                onClick = { onRouteSelected(route) },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = MaterialTheme.colorScheme.primary,
                    selectedTextColor = MaterialTheme.colorScheme.primary,
                    indicatorColor = MaterialTheme.colorScheme.secondaryContainer,
                    unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                    unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant
                )
            )
        }
    }
}















