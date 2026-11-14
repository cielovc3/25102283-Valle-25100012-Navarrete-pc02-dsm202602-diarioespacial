package pe.esan.diarioespacial.presentation.asteroid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import pe.esan.diarioespacial.data.local.AsteroidEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidFavoritesScreen(
    favorites: List<AsteroidEntity>,
    onItemClick: (String) -> Unit,
    onToggleFavorite: (AsteroidEntity) -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text("Mis asteroides")
            },
            navigationIcon = {
                IconButton(
                    onClick = onBack
                ) {
                    Icon(
                        Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = "Volver"
                    )
                }
            }
        )

        if (favorites.isEmpty()) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("Aún no tienes asteroides favoritos")
            }

        } else {

            LazyColumn {

                items(
                    items = favorites,
                    key = { it.id }
                ) { asteroid ->

                    AsteroidItem(
                        asteroid = asteroid,
                        onClick = {
                            onItemClick(asteroid.id)
                        },
                        onToggleFavorite = {
                            onToggleFavorite(asteroid)
                        }
                    )
                }
            }
        }
    }
}