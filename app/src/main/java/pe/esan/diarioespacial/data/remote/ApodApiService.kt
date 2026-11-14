package pe.esan.diarioespacial.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    // v1 (api.nasa.gov): se deja como referencia
    @GET("planetary/apod")
    suspend fun getApods(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): List<ApodDto>

    // v2: APOD migró a science.nasa.gov. La URL completa reemplaza a la URL base de Retrofit
    @GET("https://science.nasa.gov/wp-json/wp/v2/apod-basic")
    suspend fun getApodsV2(): List<ApodDto>
}