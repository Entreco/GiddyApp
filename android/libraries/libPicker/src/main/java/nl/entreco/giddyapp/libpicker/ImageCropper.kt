package nl.entreco.giddyapp.libpicker

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.util.Log
import androidx.core.graphics.BitmapCompat
import androidx.palette.graphics.Palette
import nl.entreco.giddyapp.core.HexString
import java.io.FileInputStream
import java.io.FileOutputStream

internal class ImageCropper(private val image: SelectedImage) {

    private var croppedImage: SelectedImage? = null

    internal fun resizeTo(width: Int, height: Int): ImageCropper {

        val scale = extractScale(image.uri, width, height)
        val small = scale(image.uri, scale)

        val noWay = BitmapCompat.getAllocationByteCount(small!!)
        Log.i("WAAAT", "noWay: $noWay")

        val topSwatch = Palette.from(small).setRegion(0, 0, width, height / 4).generate().dominantSwatch
        val startColor = HexString.from(topSwatch?.rgb)
        val bottomSwatch = Palette.from(small).setRegion(0, 3 * height / 4, width, height).generate().dominantSwatch
        val endColor = HexString.from(bottomSwatch?.rgb)

        croppedImage = image.copy(startColor = startColor, endColor = endColor)

        return this
    }

    private fun scale(
        uri: Uri,
        scale: Int
    ): Bitmap? {
        return FileInputStream(uri.path).use {
            val scaleOptions = BitmapFactory.Options().apply { inSampleSize = scale }
            BitmapFactory.decodeStream(it, null, scaleOptions)?.apply {
                FileOutputStream(uri.path).use { fos ->
                    compress(Bitmap.CompressFormat.JPEG, 100, fos)
                }
            }
        }
    }

    private fun extractScale(
        uri: Uri,
        width: Int,
        height: Int
    ): Int {
        return FileInputStream(uri.path).use { fissa ->

            val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeStream(fissa, null, opts)
            var w = opts.outWidth
            var h = opts.outHeight

            var scale = 1
            while (true) {
                if (w / 2 < width || h / 2 < height) break
                w /= 2
                h /= 2
                scale *= 2
            }

            scale
        }
    }

    internal fun build(): SelectedImage {
        return croppedImage ?: throw IllegalStateException("Need to crop first dude")
    }
}