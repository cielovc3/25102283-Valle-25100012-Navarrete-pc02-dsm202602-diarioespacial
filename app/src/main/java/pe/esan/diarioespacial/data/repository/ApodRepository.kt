package pe.esan.diarioespacial.data.repository

import kotlinx.coroutines.flow.Flow
import pe.esan.diarioespacial.data.local.ApodDao
import pe.esan.diarioespacial.data.local.ApodEntity
import pe.esan.diarioespacial.data.mapper.toEntity
import pe.esan.diarioespacial.data.remote.ApodApiService
import pe.esan.diarioespacial.data.remote.RetrofitInstance
import retrofit2.HttpException
import java.time.LocalDate

class ApodRepository(private val dao: ApodDao) {

    private val api = RetrofitInstance.retrofit.create(ApodApiService::class.java)
    private val startDate = LocalDate.now().minusDays(6).toString()   // desde hace 6 días

    // Las pantallas SIEMPRE leen de Room
    fun getLast7Days(): Flow<List<ApodEntity>> = dao.getFrom(startDate)
    fun getFavorites(): Flow<List<ApodEntity>> = dao.getFavorites()
    fun getByDate(date: String): Flow<ApodEntity?> = dao.getByDate(date)

    suspend fun setFavorite(date: String, isFavorite: Boolean) = dao.setFavorite(date, isFavorite)

    // API -> DTO -> Entity -> Room
    suspend fun refresh(): Result<Unit> = try {
        val today = LocalDate.now()
        val dtos = try {
            api.getApods(startDate, today.toString(), RetrofitInstance.apiKey)
        } catch (e: HttpException) {
            // Si la imagen de hoy aún no se publica, la API responde 400: pedimos hasta ayer
            if (e.code() == 400) api.getApods(startDate, today.minusDays(1).toString(), RetrofitInstance.apiKey)
            else throw e
        }
        val favoriteDates = dao.getFavoriteDates().toSet()
        dao.insertAll(dtos.mapNotNull { it.toEntity(favoriteDates) })
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}