package pe.esan.diarioespacial.data.mapper

import android.text.Html
import pe.esan.diarioespacial.data.local.ApodEntity
import pe.esan.diarioespacial.data.remote.ApodDto

// En v2 la explicación y el autor vienen con etiquetas HTML: las quitamos
private fun limpiarHtml(texto: String?): String? =
    texto?.let { Html.fromHtml(it, Html.FROM_HTML_MODE_LEGACY).toString().trim() }

// Conversión explícita DTO -> Entity
fun ApodDto.toEntity(favoriteDates: Set<String>): ApodEntity? {
    val apodDate = date ?: return null
    return ApodEntity(
        date = apodDate,
        title = title ?: "Sin título",
        explanation = limpiarHtml(explanation) ?: "",
        url = hdurl ?: url ?: "",            // en v2 "url" es la página web; la imagen está en hdurl
        hdurl = hdurl,
        mediaType = mediaType ?: "image",
        copyright = limpiarHtml(copyright),
        isFavorite = apodDate in favoriteDates
    )
}