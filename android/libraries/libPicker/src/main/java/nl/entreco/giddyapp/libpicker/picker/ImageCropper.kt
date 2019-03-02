package nl.entreco.giddyapp.libpicker.picker

import android.app.Activity
import android.graphics.Bitmap
import androidx.palette.graphics.Palette
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libpicker.SelectedImage
import javax.inject.Inject

internal class ImageCropper(private val activity: Activity) {

    private var croppedImage: SelectedImage? = null

    internal fun resizeTo(bitmap: Bitmap, width: Int, height: Int, image: SelectedImage): ImageCropper {

        extractScale(bitmap, width * 1F, height * 1F, image).also { resized ->

            val topSwatch = Palette.from(resized).setRegion(0, 0, width, height / 4).generate().dominantSwatch
            val startColor = HexString.from(topSwatch?.rgb)
            val bottomSwatch =
                Palette.from(resized).setRegion(0, 3 * height / 4, width, height).generate().dominantSwatch
            val endColor = HexString.from(bottomSwatch?.rgb)

            croppedImage = image.copy(startColor = startColor, endColor = endColor)
        }

        return this
    }

    private fun extractScale(
        bitmap: Bitmap,
        maxWidth: Float,
        maxHeight: Float,
        image: SelectedImage
    ): Bitmap {
        var originalWidth = bitmap.width * 1F
        var originalHeight = bitmap.height * 1F

        val shouldDownScaleWidth = maxWidth < originalWidth
        val shouldDownScaleHeight = maxHeight < originalHeight
        val shouldDownScale = shouldDownScaleWidth || shouldDownScaleHeight

        var scale = 1
        if (shouldDownScale) {

            while (true) {
                if (originalWidth / 2 < maxWidth || originalHeight / 2 < maxHeight) break
                originalWidth /= 2
                originalHeight /= 2
                scale *= 2
            }
        } else {
            // Throw error if too small image...
        }

        val scaled = Bitmap.createScaledBitmap(bitmap, maxWidth.toInt(), maxHeight.toInt(), false)
        activity.contentResolver.openOutputStream(image.uri)?.use { output ->
            scaled.compress(Bitmap.CompressFormat.JPEG, 100, output)
        }
        return scaled
    }

    internal fun build(): SelectedImage {
        return croppedImage ?: throw IllegalStateException("Need to crop first dude")
    }
}