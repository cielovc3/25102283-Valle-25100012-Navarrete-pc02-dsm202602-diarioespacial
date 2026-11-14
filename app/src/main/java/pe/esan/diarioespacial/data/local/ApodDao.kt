package pe.esan.diarioespacial.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import kotlinx.coroutines.flow.Flow

@Dao
interface ApodDao {

    // De la más reciente a la más antigua (Flow: la pantalla se actualiza sola)
    @Query("SELECT * FROM apods WHERE date >= :fromDate ORDER BY date DESC")
    fun getFrom(fromDate: String): Flow<List<ApodEntity>>

    @Query("SELECT * FROM apods WHERE isFavorite = 1 ORDER BY date DESC")
    fun getFavorites(): Flow<List<ApodEntity>>

    @Query("SELECT * FROM apods WHERE date = :date")
    fun getByDate(date: String): Flow<ApodEntity?>

    // Se lee antes de guardar la API para no perder los favoritos
    @Query("SELECT date FROM apods WHERE isFavorite = 1")
    suspend fun getFavoriteDates(): List<String>

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAll(apods: List<ApodEntity>)

    @Query("UPDATE apods SET isFavorite = :isFavorite WHERE date = :date")
    suspend fun setFavorite(date: String, isFavorite: Boolean)
}