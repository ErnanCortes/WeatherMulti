package org.weathermulti.project

import io.ktor.client.HttpClient
import io.ktor.client.plugins.contentnegotiation.ContentNegotiation
import io.ktor.client.request.get
import io.ktor.client.request.parameter
import io.ktor.client.statement.bodyAsText
import io.ktor.serialization.kotlinx.json.json
import kotlinx.serialization.json.Json


private const val key = "fa8b3df74d4042b9aa7135114252304"

class WeatherRepository {
    private val platform: Platform = getPlatform()


    companion object {

        const val BASE_URL = "https://api.weatherapi.com/v1/forecast.json"
        const val MOSCOW_LOC = "55.75,37.62"
        const val NUMBER_OF_DAYS = "3"

        val myJson = Json {
            ignoreUnknownKeys = true  // Set this globally for the client
            prettyPrint = true        // Optional: for pretty printing (mainly for debugging)
            isLenient = true
        }


    }

    val client = HttpClient() {
        install(ContentNegotiation) {
            json(
                myJson
            )
        }
    }


    suspend fun getWeather(cords: String = MOSCOW_LOC): ApiResponse {

        val response = client.get("${BASE_URL}/forecast.json") {
            parameter("key", key)
            parameter("q", cords)
            parameter("days", NUMBER_OF_DAYS)
        }.bodyAsText()
        val jsonString = response

        println("the response is " + response)

        return myJson.decodeFromString<ApiResponse>(jsonString)



    }

}