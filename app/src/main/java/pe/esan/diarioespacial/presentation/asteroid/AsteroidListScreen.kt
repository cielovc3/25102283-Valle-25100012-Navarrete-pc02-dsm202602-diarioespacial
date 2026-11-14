package pe.esan.diarioespacial.presentation.asteroid

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidListScreen(
    viewModel: AsteroidViewModel,
    onItemClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val asteroids by viewModel.asteroids.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()
    val onlyHazardous by viewModel.onlyHazardous.collectAsState()

    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text("Asteroides")
            },
            actions = {
                TextButton(
                    onClick = onFavoritesClick
                ) {
                    Text("Mis asteroides")
                }
            }
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Text(
                text = "Solo potencialmente peligrosos",
                modifier = Modifier.weight(1f)
            )

            Switch(
                checked = onlyHazardous,
                onCheckedChange = {
                    viewModel.setOnlyHazardous(it)
                }
            )
        }

        when {

            isLoading -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    CircularProgressIndicator()
                }
            }

            asteroids.isEmpty() -> {

                Box(
                    modifier = Modifier.fillMaxSize(),
                    contentAlignment = Alignment.Center
                ) {
                    Text(
                        error ?: "No hay asteroides para mostrar"
                    )
                }
            }

            else -> {

                LazyColumn {

                    items(
                        items = asteroids,
                        key = { it.id }
                    ) { asteroid ->

                        AsteroidItem(
                            asteroid = asteroid,
                            onClick = {
                                onItemClick(asteroid.id)
                            },
                            onToggleFavorite = {
                                viewModel.toggleFavorite(asteroid)
                            }
                        )
                    }
                }
            }
        }
    }
}