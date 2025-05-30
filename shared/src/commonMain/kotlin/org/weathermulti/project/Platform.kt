package org.weathermulti.project

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform