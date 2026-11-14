package pe.esan.diarioespacial.presentation.apod

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
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
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import pe.esan.diarioespacial.data.local.ApodEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodDetailScreen(apod: ApodEntity?, onToggleFavorite: () -> Unit, onBack: () -> Unit) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Detalle de imagen") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            },
            actions = {
                if (apod != null) {
                    IconButton(onClick = onToggleFavorite) {
                        Icon(
                            imageVector = if (apod.isFavorite) Icons.Filled.Favorite else Icons.Filled.FavoriteBorder,
                            contentDescription = "Favorito",
                            tint = if (apod.isFavorite) Color.Red else Color.Gray
                        )
                    }
                }
            }
        )
        if (apod == null) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("No se encontró la imagen")
            }
        } else {
            Column(
                Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(16.dp)
            ) {
                if (apod.mediaType == "video") {
                    Text("Contenido en video", style = MaterialTheme.typography.bodyLarge)
                } else {
                    AsyncImage(
                        model = apod.hdurl ?: apod.url,   // hdurl si existe; si no, url
                        contentDescription = apod.title,
                        contentScale = ContentScale.Fit,
                        modifier = Modifier.fillMaxWidth().height(300.dp)
                    )
                }
                Spacer(Modifier.height(12.dp))
                Text(apod.title, style = MaterialTheme.typography.headlineSmall)
                Text(apod.date, style = MaterialTheme.typography.bodyMedium)
                if (apod.copyright != null) {
                    Text("Autor: ${apod.copyright}", style = MaterialTheme.typography.bodyMedium)
                }
                Spacer(Modifier.height(12.dp))
                Text(apod.explanation, style = MaterialTheme.typography.bodyLarge)
            }
        }
    }
}