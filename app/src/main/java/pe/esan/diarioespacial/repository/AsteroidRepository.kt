package pe.esan.diarioespacial.data.repository

import kotlinx.coroutines.flow.Flow
import pe.esan.diarioespacial.data.local.AsteroidDao
import pe.esan.diarioespacial.data.local.AsteroidEntity
import pe.esan.diarioespacial.data.mapper.toEntities
import pe.esan.diarioespacial.data.remote.NeoWsApiService
import pe.esan.diarioespacial.data.remote.RetrofitInstance
import java.time.LocalDate

class AsteroidRepository(
    private val dao: AsteroidDao
) {

    private val api =
        RetrofitInstance.retrofit.create(NeoWsApiService::class.java)

    private val startDate =
        LocalDate.now().minusDays(6).toString()

    fun getLast7Days(): Flow<List<AsteroidEntity>> =
        dao.getFrom(startDate)

    fun getFavorites(): Flow<List<AsteroidEntity>> =
        dao.getFavorites()

    fun getById(id: String): Flow<AsteroidEntity?> =
        dao.getById(id)

    suspend fun setFavorite(
        id: String,
        isFavorite: Boolean
    ) = dao.setFavorite(id, isFavorite)

    suspend fun refresh(): Result<Unit> =
        try {

            val feed = api.getFeed(
                startDate = startDate,
                endDate = LocalDate.now().toString(),
                apiKey = RetrofitInstance.apiKey
            )

            val favoriteIds =
                dao.getFavoriteIds().toSet()

            dao.insertAll(
                feed.toEntities(favoriteIds)
            )

            Result.success(Unit)

        } catch (e: Exception) {

            Result.failure(e)
        }
}