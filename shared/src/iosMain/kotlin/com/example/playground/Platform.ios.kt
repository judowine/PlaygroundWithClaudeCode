package com.example.playground

import platform.UIKit.UIDevice
import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

class IOSPlatform: Platform {
    override val name: String = UIDevice.currentDevice.systemName() + " " + UIDevice.currentDevice.systemVersion
}

actual fun getPlatform(): Platform = IOSPlatform()

/**
 * iOS implementation for getting current time in milliseconds
 * @return Current timestamp in milliseconds since epoch
 */
actual fun getCurrentTimeMillis(): Long = (NSDate().timeIntervalSince1970() * 1000).toLong()