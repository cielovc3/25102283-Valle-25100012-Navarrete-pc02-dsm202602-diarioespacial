package pe.esan.diarioespacial.presentation.apod

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import pe.esan.diarioespacial.presentation.navigation.Routes

private const val APOD_LIST = "apod_list"
private const val APOD_DETAIL = "apod_detail/{date}"
private const val APOD_FAVORITES = "apod_favorites"

fun NavGraphBuilder.imagenesGraph(navController: NavHostController, viewModel: ApodViewModel) {
    navigation(startDestination = APOD_LIST, route = Routes.IMAGENES) {

        composable(APOD_LIST) {
            ApodListScreen(
                viewModel = viewModel,
                onItemClick = { date -> navController.navigate("apod_detail/$date") },
                onFavoritesClick = { navController.navigate(APOD_FAVORITES) }
            )
        }

        composable(APOD_DETAIL) { entry ->
            val date = entry.arguments?.getString("date") ?: ""
            // El detalle se lee de Room por fecha
            val apod by remember(date) { viewModel.getByDate(date) }.collectAsState(initial = null)
            ApodDetailScreen(
                apod = apod,
                onToggleFavorite = { apod?.let { viewModel.toggleFavorite(it) } },
                onBack = { navController.popBackStack() }
            )
        }

        composable(APOD_FAVORITES) {
            val favorites by viewModel.favorites.collectAsState()
            ApodFavoritesScreen(
                favorites = favorites,
                onItemClick = { date -> navController.navigate("apod_detail/$date") },
                onToggleFavorite = { viewModel.toggleFavorite(it) },
                onBack = { navController.popBackStack() }
            )
        }
    }
}