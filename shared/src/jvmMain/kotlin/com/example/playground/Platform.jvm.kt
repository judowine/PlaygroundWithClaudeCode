package com.example.playground

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

/**
 * JVM implementation for getting current time in milliseconds
 * @return Current timestamp in milliseconds since epoch
 */
actual fun getCurrentTimeMillis(): Long = System.currentTimeMillis()