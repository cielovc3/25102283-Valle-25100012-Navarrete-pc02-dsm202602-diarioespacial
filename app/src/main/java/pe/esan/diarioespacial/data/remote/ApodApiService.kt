package pe.esan.diarioespacial.data.remote

import retrofit2.http.GET
import retrofit2.http.Query

interface ApodApiService {
    // GET https://api.nasa.gov/planetary/apod?start_date=...&end_date=...&api_key=...
    @GET("planetary/apod")
    suspend fun getApods(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("api_key") apiKey: String
    ): List<ApodDto>
}