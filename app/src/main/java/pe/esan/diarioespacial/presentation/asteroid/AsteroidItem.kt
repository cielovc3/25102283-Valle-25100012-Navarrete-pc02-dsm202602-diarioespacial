package pe.esan.diarioespacial.presentation.asteroid

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import pe.esan.diarioespacial.data.local.AsteroidEntity

@Composable
fun AsteroidItem(
    asteroid: AsteroidEntity,
    onClick: () -> Unit,
    onToggleFavorite: () -> Unit
) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() },
        colors = CardDefaults.cardColors(
            containerColor =
                if (asteroid.isHazardous)
                    MaterialTheme.colorScheme.errorContainer
                else
                    MaterialTheme.colorScheme.surfaceVariant
        )
    ) {
        Row(
            modifier = Modifier.padding(12.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {

            Column(
                modifier = Modifier.weight(1f)
            ) {

                Text(
                    asteroid.name,
                    style = MaterialTheme.typography.titleMedium
                )

                if (asteroid.isHazardous) {
                    Text(
                        "⚠ Potencialmente peligroso",
                        color = MaterialTheme.colorScheme.error,
                        style = MaterialTheme.typography.labelLarge
                    )
                }

                Text("Aproximación: ${asteroid.approachDate}")
                Text("Diámetro máx.: ${formato2(asteroid.diameterMaxM)} m")
                Text("Velocidad: ${formato2(asteroid.velocityKmh)} km/h")
                Text("Distancia: ${formato2(asteroid.missDistanceKm)} km")
            }

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
}