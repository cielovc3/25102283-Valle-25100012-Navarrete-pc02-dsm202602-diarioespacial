package pe.esan.diarioespacial.presentation.asteroid

import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
import pe.esan.diarioespacial.presentation.navigation.Routes

private const val ASTEROID_LIST = "asteroid_list"
private const val ASTEROID_DETAIL = "asteroid_detail/{id}"
private const val ASTEROID_FAVORITES = "asteroid_favorites"

fun NavGraphBuilder.asteroidesGraph(
    navController: NavHostController,
    viewModel: AsteroidViewModel
) {
    navigation(
        startDestination = ASTEROID_LIST,
        route = Routes.ASTEROIDES
    ) {

        composable(ASTEROID_LIST) {
            AsteroidListScreen(
                viewModel = viewModel,
                onItemClick = { id ->
                    navController.navigate("asteroid_detail/$id")
                },
                onFavoritesClick = {
                    navController.navigate(ASTEROID_FAVORITES)
                }
            )
        }

        composable(ASTEROID_DETAIL) { entry ->

            val id =
                entry.arguments?.getString("id") ?: ""

            val asteroid by remember(id) {
                viewModel.getById(id)
            }.collectAsState(initial = null)

            AsteroidDetailScreen(
                asteroid = asteroid,
                onToggleFavorite = {
                    asteroid?.let {
                        viewModel.toggleFavorite(it)
                    }
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }

        composable(ASTEROID_FAVORITES) {

            val favorites by
            viewModel.favorites.collectAsState()

            AsteroidFavoritesScreen(
                favorites = favorites,
                onItemClick = { id ->
                    navController.navigate("asteroid_detail/$id")
                },
                onToggleFavorite = {
                    viewModel.toggleFavorite(it)
                },
                onBack = {
                    navController.popBackStack()
                }
            )
        }
    }
}