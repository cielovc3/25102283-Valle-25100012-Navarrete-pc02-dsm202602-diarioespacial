package pe.esan.diarioespacial.data.mapper

import pe.esan.diarioespacial.data.local.ApodEntity
import pe.esan.diarioespacial.data.remote.ApodDto

// Conversión explícita DTO -> Entity; los nulos reciben un valor por defecto
fun ApodDto.toEntity(favoriteDates: Set<String>): ApodEntity? {
    val apodDate = date ?: return null   // sin fecha no hay llave primaria
    return ApodEntity(
        date = apodDate,
        title = title ?: "Sin título",
        explanation = explanation ?: "",
        url = url ?: "",
        hdurl = hdurl,
        mediaType = mediaType ?: "image",
        copyright = copyright?.trim(),
        isFavorite = apodDate in favoriteDates   // conserva el favorito al recargar
    )
}