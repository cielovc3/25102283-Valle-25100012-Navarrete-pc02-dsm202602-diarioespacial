package pe.esan.diarioespacial.presentation.apod

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.FavoriteBorder
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pe.esan.diarioespacial.data.local.ApodEntity

@Composable
fun ApodItem(apod: ApodEntity, onClick: () -> Unit, onToggleFavorite: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .clickable { onClick() }
    ) {
        Column(Modifier.padding(12.dp)) {
            // Si es video NO se intenta cargar imagen
            if (apod.mediaType == "video") {
                Text("Contenido en video", style = MaterialTheme.typography.bodyLarge)
            } else {
                AsyncImage(
                    model = apod.url,
                    contentDescription = apod.title,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.fillMaxWidth().height(180.dp)
                )
            }
            Row(verticalAlignment = Alignment.CenterVertically) {
                Column(Modifier.weight(1f)) {
                    Text(apod.title, style = MaterialTheme.typography.titleMedium)
                    Text(apod.date, style = MaterialTheme.typography.bodySmall)
                }
                // El corazón cambia según el estado
                IconButton(onClick = onToggleFavorite) {
                    Icon(
                        imageVector = if (apod.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                        contentDescription = "Favorito",
                        tint = if (apod.isFavorite) Color.Red else Color.Gray
                    )
                }
            }
        }
    }
}