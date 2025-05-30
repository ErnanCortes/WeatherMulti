package org.weathermulti.project

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable


@Serializable
data class ApiResponse(
    val location: Location,
    val current: Current,
    val forecast: Forecast
)

@Serializable
data class Location(
    val name: String,
    val region: String,
    val country: String
    // add other fields you need, or enable ignoreUnknownKeys
)

@Serializable
data class Current(
    val temp_c: Float
    // add other fields as needed
)

@Serializable
data class Forecast(
    val forecastday: List<ForecastDay>
)

@Serializable
data class ForecastDay(
    val date: String,
    val day: Day
)

@Serializable
data class Day(
    val maxtemp_c: Float,
    val mintemp_c: Float,
    val avgtemp_c: Float,
    // add other fields as needed
)

@Serializable
data class Restaurant(
    @SerialName("r_id") val id: Int,
    @SerialName("r_title") val title: String,
    @SerialName("r_description") val description: String,
    var isFavorite: Boolean = false
)