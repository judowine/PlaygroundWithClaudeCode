package com.example.playground

import android.os.Build

class AndroidPlatform : Platform {
    override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

/**
 * Android implementation for getting current time in milliseconds
 * @return Current timestamp in milliseconds since epoch
 */
actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()