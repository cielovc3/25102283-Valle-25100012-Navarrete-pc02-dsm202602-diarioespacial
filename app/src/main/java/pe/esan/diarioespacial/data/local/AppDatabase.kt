package pe.esan.diarioespacial.data.local

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase

// Base común. Acuerdo: el Alumno 2 agrega AsteroidEntity, version = 2 y asteroidDao()
@Database(
    entities = [ApodEntity::class, AsteroidEntity::class],
    version = 2,
    exportSchema = false
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun apodDao(): ApodDao
    abstract fun asteroidDao(): AsteroidDao

    companion object {
        @Volatile
        private var INSTANCE: AppDatabase? = null

        // Singleton: una sola instancia de la base en toda la app
        fun getInstance(context: Context): AppDatabase =
            INSTANCE ?: synchronized(this) {
                INSTANCE ?: Room.databaseBuilder(
                    context.applicationContext,
                    AppDatabase::class.java,
                    "diario_espacial.db"
                )
                    .fallbackToDestructiveMigration(true)   // al subir de versión recrea las tablas
                    .build().also { INSTANCE = it }
            }
    }
}