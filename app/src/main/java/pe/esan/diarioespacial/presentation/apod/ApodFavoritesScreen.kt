package pe.esan.diarioespacial.presentation.apod

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
import pe.esan.diarioespacial.data.local.ApodEntity

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodFavoritesScreen(
    favorites: List<ApodEntity>,
    onItemClick: (String) -> Unit,
    onToggleFavorite: (ApodEntity) -> Unit,
    onBack: () -> Unit
) {
    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Mis imágenes favoritas") },
            navigationIcon = {
                IconButton(onClick = onBack) {
                    Icon(Icons.AutoMirrored.Filled.ArrowBack, contentDescription = "Volver")
                }
            }
        )
        if (favorites.isEmpty()) {
            Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text("Aún no tienes imágenes favoritas")
            }
        } else {
            LazyColumn {
                items(favorites, key = { it.date }) { apod ->
                    ApodItem(
                        apod = apod,
                        onClick = { onItemClick(apod.date) },
                        onToggleFavorite = { onToggleFavorite(apod) }   // quita de favoritos
                    )
                }
            }
        }
    }
}