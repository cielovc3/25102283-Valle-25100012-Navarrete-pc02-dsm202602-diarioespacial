package pe.esan.diarioespacial.data.remote

import okhttp3.OkHttpClient
import pe.esan.diarioespacial.BuildConfig
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

// Base común: cada opción crea su propio servicio con retrofit.create(...)
object RetrofitInstance {
    private const val BASE_URL = "https://api.nasa.gov/"

    private val client = OkHttpClient.Builder()
        .connectTimeout(30, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)   // APOD a veces tarda
        .build()

    val retrofit: Retrofit = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(client)
        .addConverterFactory(GsonConverterFactory.create())
        .build()

    // La clave viene de local.properties -> BuildConfig (no está en el repo)
    val apiKey: String = BuildConfig.NASA_API_KEY
}