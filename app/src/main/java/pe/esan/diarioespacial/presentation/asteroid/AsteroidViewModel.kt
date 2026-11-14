package pe.esan.diarioespacial.presentation.asteroid

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pe.esan.diarioespacial.data.local.AppDatabase
import pe.esan.diarioespacial.data.local.AsteroidEntity
import pe.esan.diarioespacial.data.repository.AsteroidRepository

class AsteroidViewModel(
    application: Application
) : AndroidViewModel(application) {

    private val repository =
        AsteroidRepository(
            AppDatabase.getInstance(application).asteroidDao()
        )

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> =
        _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> =
        _error.asStateFlow()

    private val _onlyHazardous =
        MutableStateFlow(false)

    val onlyHazardous: StateFlow<Boolean> =
        _onlyHazardous.asStateFlow()

    val asteroids: StateFlow<List<AsteroidEntity>> =
        combine(
            repository.getLast7Days(),
            _onlyHazardous
        ) { list, only ->

            if (only) {
                list.filter { it.isHazardous }
            } else {
                list
            }

        }.stateIn(
            viewModelScope,
            SharingStarted.WhileSubscribed(5000),
            emptyList()
        )

    val favorites: StateFlow<List<AsteroidEntity>> =
        repository
            .getFavorites()
            .stateIn(
                viewModelScope,
                SharingStarted.WhileSubscribed(5000),
                emptyList()
            )

    init {
        refresh()
    }

    fun refresh() {
        viewModelScope.launch {

            _isLoading.value = true
            _error.value = null

            repository.refresh()
                .onFailure {
                    _error.value =
                        "No se pudo conectar con la API"
                }

            _isLoading.value = false
        }
    }

    fun setOnlyHazardous(value: Boolean) {
        _onlyHazardous.value = value
    }

    fun getById(
        id: String
    ): Flow<AsteroidEntity?> =
        repository.getById(id)

    fun toggleFavorite(
        asteroid: AsteroidEntity
    ) {
        viewModelScope.launch {
            repository.setFavorite(
                asteroid.id,
                !asteroid.isFavorite
            )
        }
    }
}