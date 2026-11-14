package pe.esan.diarioespacial.data.remote

import com.google.gson.annotations.SerializedName

data class NeoFeedDto(
    @SerializedName("near_earth_objects")
    val nearEarthObjects: Map<String, List<NeoDto>>?
)

data class NeoDto(
    val id: String?,
    val name: String?,
    @SerializedName("is_potentially_hazardous_asteroid")
    val isHazardous: Boolean?,
    @SerializedName("estimated_diameter")
    val estimatedDiameter: EstimatedDiameterDto?,
    @SerializedName("close_approach_data")
    val closeApproachData: List<CloseApproachDto>?
)

data class EstimatedDiameterDto(
    val meters: DiameterDto?
)

data class DiameterDto(
    @SerializedName("estimated_diameter_min")
    val min: Double?,
    @SerializedName("estimated_diameter_max")
    val max: Double?
)

data class CloseApproachDto(
    @SerializedName("close_approach_date")
    val date: String?,
    @SerializedName("relative_velocity")
    val relativeVelocity: VelocityDto?,
    @SerializedName("miss_distance")
    val missDistance: MissDistanceDto?
)

data class VelocityDto(
    @SerializedName("kilometers_per_hour")
    val kilometersPerHour: String?
)

data class MissDistanceDto(
    val kilometers: String?
)