package pe.esan.diarioespacial.data.repository

import kotlinx.coroutines.flow.Flow
import pe.esan.diarioespacial.data.local.ApodDao
import pe.esan.diarioespacial.data.local.ApodEntity
import pe.esan.diarioespacial.data.mapper.toEntity
import pe.esan.diarioespacial.data.remote.ApodApiService
import pe.esan.diarioespacial.data.remote.RetrofitInstance
import java.time.LocalDate

class ApodRepository(private val dao: ApodDao) {

    private val api = RetrofitInstance.retrofit.create(ApodApiService::class.java)
    private val startDate = LocalDate.now().minusDays(6).toString()

    // Las pantallas leen de Room; el DAO filtra los últimos 7 días
    fun getLast7Days(): Flow<List<ApodEntity>> = dao.getFrom(startDate)
    fun getFavorites(): Flow<List<ApodEntity>> = dao.getFavorites()
    fun getByDate(date: String): Flow<ApodEntity?> = dao.getByDate(date)

    suspend fun setFavorite(date: String, isFavorite: Boolean) = dao.setFavorite(date, isFavorite)

    // API v2 -> DTO -> Entity -> Room
    suspend fun refresh(): Result<Unit> = try {
        val dtos = api.getApodsV2()
        val favoriteDates = dao.getFavoriteDates().toSet()
        dao.insertAll(dtos.mapNotNull { it.toEntity(favoriteDates) })   // REPLACE pisa las filas "NASA Science"
        Result.success(Unit)
    } catch (e: Exception) {
        Result.failure(e)
    }
}