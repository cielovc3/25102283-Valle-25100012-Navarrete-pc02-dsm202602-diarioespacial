package pe.esan.diarioespacial.presentation.apod

import android.app.Application
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import pe.esan.diarioespacial.data.local.AppDatabase
import pe.esan.diarioespacial.data.local.ApodEntity
import pe.esan.diarioespacial.data.repository.ApodRepository

class ApodViewModel(application: Application) : AndroidViewModel(application) {

    private val repository = ApodRepository(AppDatabase.getInstance(application).apodDao())

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading.asStateFlow()

    private val _error = MutableStateFlow<String?>(null)
    val error: StateFlow<String?> = _error.asStateFlow()

    // Lista y favoritas vienen de Room (Flow -> StateFlow)
    val apods: StateFlow<List<ApodEntity>> =
        repository.getLast7Days().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    val favorites: StateFlow<List<ApodEntity>> =
        repository.getFavorites().stateIn(viewModelScope, SharingStarted.WhileSubscribed(5000), emptyList())

    init {
        refresh()   // al abrir la opción se consulta la API
    }

    fun refresh() {
        viewModelScope.launch {
            _isLoading.value = true
            _error.value = null
            repository.refresh().onFailure { _error.value = "No se pudo conectar con la API" }
            _isLoading.value = false
        }
    }

    fun getByDate(date: String): Flow<ApodEntity?> = repository.getByDate(date)

    fun toggleFavorite(apod: ApodEntity) {
        viewModelScope.launch { repository.setFavorite(apod.date, !apod.isFavorite) }
    }
}