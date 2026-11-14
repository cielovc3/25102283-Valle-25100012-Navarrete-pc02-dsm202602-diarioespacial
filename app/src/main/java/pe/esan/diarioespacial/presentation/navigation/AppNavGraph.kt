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
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavDestination.Companion.hierarchy
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import pe.esan.diarioespacial.presentation.apod.ApodViewModel
import pe.esan.diarioespacial.presentation.apod.imagenesGraph
import pe.esan.diarioespacial.presentation.asteroid.AsteroidViewModel
import pe.esan.diarioespacial.presentation.asteroid.asteroidesGraph

@Composable
fun AppNavGraph() {
    val navController = rememberNavController()
    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = backStackEntry?.destination

    // Un ViewModel por opción, compartido por sus pantallas internas
    val apodViewModel: ApodViewModel = viewModel()
    val asteroidViewModel: AsteroidViewModel = viewModel()

    Scaffold(
        contentWindowInsets = WindowInsets(0, 0, 0, 0),
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
            imagenesGraph(navController, apodViewModel)          // Alumno 1
            asteroidesGraph(navController, asteroidViewModel)    // Alumno 2
        }
    }
}

private fun navigateToTab(navController: NavHostController, route: String) {
    navController.navigate(route) {
        popUpTo(navController.graph.findStartDestination().id) { saveState = true }
        launchSingleTop = true
        restoreState = true
    }
}