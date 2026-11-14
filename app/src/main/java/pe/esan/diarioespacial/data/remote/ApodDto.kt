package pe.esan.diarioespacial.data.remote

import com.google.gson.annotations.SerializedName

// Todo nullable: si falta un campo (hdurl, copyright...) la app no se cae
data class ApodDto(
    val date: String?,
    val title: String?,
    val explanation: String?,
    val url: String?,
    val hdurl: String?,
    @SerializedName("media_type") val mediaType: String?,
    val copyright: String?
)