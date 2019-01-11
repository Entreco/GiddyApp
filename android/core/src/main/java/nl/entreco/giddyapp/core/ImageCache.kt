package nl.entreco.giddyapp.core

import android.graphics.Bitmap
import javax.inject.Inject

class ImageCache @Inject constructor() {
    private val cache by lazy { mutableMapOf<String, Bitmap?>() }

    fun put(key: String, value: Bitmap?) {
        cache[key] = value
    }

    fun get(key: String): Bitmap? {
        return cache.getOrElse(key) { null }
    }
}