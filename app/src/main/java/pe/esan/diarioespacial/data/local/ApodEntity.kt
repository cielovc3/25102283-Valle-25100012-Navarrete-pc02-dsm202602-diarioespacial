package pe.esan.diarioespacial.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

// Tabla "apods": una fila por imagen del día
@Entity(tableName = "apods")
data class ApodEntity(
    @PrimaryKey val date: String,   // AAAA-MM-DD, única por imagen
    val title: String,
    val explanation: String,
    val url: String,
    val hdurl: String?,             // puede no venir
    val mediaType: String,          // "image" o "video"
    val copyright: String?,         // puede no venir
    val isFavorite: Boolean = false
)