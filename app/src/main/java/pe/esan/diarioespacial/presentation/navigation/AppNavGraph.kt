package pe.esan.diarioespacial.presentation.navigation

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.filled.Warning
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),   // cada pantalla maneja su barra superior
        bottomBar = {
            NavigationBar {
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.IMAGENES } == true,
                    onClick = { navigateToTab(navController, Routes.IMAGENES) },
                    icon = { Icon(Icons.Filled.Star, contentDescription = null) },
                    label = { Text("Imágenes") }
                )
                NavigationBarItem(
                    selected = currentDestination?.hierarchy?.any { it.route == Routes.ASTEROIDES } == true,
                    onClick = { navigateToTab(navController, Routes.ASTEROIDES) },
                    icon = { Icon(Icons.Filled.Warning, contentDescription = null) },
                    label = { Text("Asteroides") }
                )
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = Routes.IMAGENES,
            modifier = Modifier.padding(innerPadding)
        ) {
            // Temporales: se reemplazan por imagenesGraph(...) y asteroidesGraph(...)
            composable(Routes.IMAGENES) { Text("Imágenes (pendiente)") }
            composable(Routes.ASTEROIDES) { Text("Asteroides (pendiente)") }
        }
    }
}

// Cambia de opción sin apilar pantallas y recordando dónde estabas
private fun navigateToTab(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}