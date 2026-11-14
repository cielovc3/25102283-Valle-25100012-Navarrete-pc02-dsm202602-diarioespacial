package pe.esan.diarioespacial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface AsteroidDao {

    @Query("SELECT * FROM asteroids WHERE approachDate >= :fromDate ORDER BY approachDate DESC, name ASC")
    fun getFrom(fromDate: String): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE isFavorite = 1 ORDER BY approachDate DESC")
    fun getFavorites(): Flow<List<AsteroidEntity>>

    @Query("SELECT * FROM asteroids WHERE id = :id")
    fun getById(id: String): Flow<AsteroidEntity?>

    @Query("SELECT id FROM asteroids WHERE isFavorite = 1")
    suspend fun getFavoriteIds(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(asteroids: List<AsteroidEntity>)

    @Query("UPDATE asteroids SET isFavorite = :isFavorite WHERE id = :id")
    suspend fun setFavorite(id: String, isFavorite: Boolean)
}