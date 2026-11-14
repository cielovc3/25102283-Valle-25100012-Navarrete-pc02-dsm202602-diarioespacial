package pe.esan.diarioespacial.data.mapper

import pe.esan.diarioespacial.data.local.AsteroidEntity
import pe.esan.diarioespacial.data.remote.NeoDto
import pe.esan.diarioespacial.data.remote.NeoFeedDto

fun NeoFeedDto.toEntities(favoriteIds: Set<String>): List<AsteroidEntity> =
    nearEarthObjects.orEmpty().values.flatten().mapNotNull { it.toEntity(favoriteIds) }

fun NeoDto.toEntity(favoriteIds: Set<String>): AsteroidEntity? {
    val asteroidId = id ?: return null
    val approach = closeApproachData?.firstOrNull()

    return AsteroidEntity(
        id = asteroidId,
        name = name ?: "Sin nombre",
        approachDate = approach?.date ?: "",
        diameterMinM = estimatedDiameter?.meters?.min ?: 0.0,
        diameterMaxM = estimatedDiameter?.meters?.max ?: 0.0,
        velocityKmh = approach?.relativeVelocity?.kilometersPerHour?.toDoubleOrNull() ?: 0.0,
        missDistanceKm = approach?.missDistance?.kilometers?.toDoubleOrNull() ?: 0.0,
        isHazardous = isHazardous ?: false,
        isFavorite = asteroidId in favoriteIds
    )
}