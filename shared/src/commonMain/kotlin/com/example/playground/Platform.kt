package com.example.playground

interface Platform {
    val name: String
}

expect fun getPlatform(): Platform

/**
 * Platform-specific implementation for getting current time in milliseconds
 * @return Current timestamp in milliseconds since epoch
 */
expect fun getCurrentTimeMillis(): Long