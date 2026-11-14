package pe.esan.diarioespacial.presentation.asteroid

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pe.esan.diarioespacial.data.local.AsteroidEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AsteroidDetailScreen(
    asteroid: AsteroidEntity?,
    onToggleFavorite: () -> Unit,
    onBack: () -> Unit
) {
    Column(
        modifier = Modifier.fillMaxSize()
    ) {

        TopAppBar(
            title = {
                Text("Detalle de asteroide")
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
            },
            actions = {

                if (asteroid != null) {

                    IconButton(
                        onClick = onToggleFavorite
                    ) {
                        Icon(
                            imageVector =
                                if (asteroid.isFavorite)
                                    Icons.Filled.Favorite
                                else
                                    Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint =
                                if (asteroid.isFavorite)
                                    Color.Red
                                else
                                    Color.Gray
                        )
                    }
                }
            }
        )

        if (asteroid == null) {

            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Text("No se encontró el asteroide")
            }

        } else {

            Column(
                modifier = Modifier.padding(16.dp),
                verticalArrangement =
                    Arrangement.spacedBy(6.dp)
            ) {

                Text(
                    asteroid.name,
                    style = MaterialTheme.typography.headlineSmall
                )

                Text("Id: ${asteroid.id}")
                Text("Fecha de aproximación: ${asteroid.approachDate}")
                Text("Diámetro mínimo estimado: ${formato2(asteroid.diameterMinM)} m")
                Text("Diámetro máximo estimado: ${formato2(asteroid.diameterMaxM)} m")
                Text("Velocidad relativa: ${formato2(asteroid.velocityKmh)} km/h")
                Text("Distancia de paso: ${formato2(asteroid.missDistanceKm)} km")

                Text(
                    text =
                        "Potencialmente peligroso: ${
                            if (asteroid.isHazardous) "Sí"
                            else "No"
                        }",
                    color =
                        if (asteroid.isHazardous)
                            MaterialTheme.colorScheme.error
                        else
                            Color.Unspecified
                )
            }
        }
    }
}