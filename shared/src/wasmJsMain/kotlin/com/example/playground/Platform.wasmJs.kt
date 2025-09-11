package com.example.playground

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

/**
 * Web/WASM implementation for getting current time in milliseconds
 * @return Current timestamp in milliseconds since epoch
 */
actual fun getCurrentTimeMillis(): Long {
    // TODO: Implement proper WASM timestamp when platform APIs are stable
    // For skeleton implementation, return a fixed timestamp
    return 1734768000000L // Dec 21, 2024 placeholder
}