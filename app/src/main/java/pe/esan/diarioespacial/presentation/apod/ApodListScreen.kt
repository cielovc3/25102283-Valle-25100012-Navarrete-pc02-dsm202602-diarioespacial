package pe.esan.diarioespacial.presentation.apod

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ApodListScreen(
    viewModel: ApodViewModel,
    onItemClick: (String) -> Unit,
    onFavoritesClick: () -> Unit
) {
    val apods by viewModel.apods.collectAsState()
    val isLoading by viewModel.isLoading.collectAsState()
    val error by viewModel.error.collectAsState()

    Column(Modifier.fillMaxSize()) {
        TopAppBar(
            title = { Text("Imágenes") },
            actions = { TextButton(onClick = onFavoritesClick) { Text("Mis favoritas") } }
        )
        when {
            isLoading -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                CircularProgressIndicator()
            }
            apods.isEmpty() -> Box(Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                Text(error ?: "No hay imágenes para mostrar")
            }
            else -> LazyColumn {
                items(apods, key = { it.date }) { apod ->
                    ApodItem(
                        apod = apod,
                        onClick = { onItemClick(apod.date) },
                        onToggleFavorite = { viewModel.toggleFavorite(apod) }
                    )
                }
            }
        }
    }
}