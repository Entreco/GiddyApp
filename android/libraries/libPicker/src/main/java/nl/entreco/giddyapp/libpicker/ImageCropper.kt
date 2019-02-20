package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import androidx.palette.graphics.Palette
import nl.entreco.giddyapp.core.HexString
import java.io.File
import java.io.IOException
import java.io.InputStream

internal class ImageCropper(private val activity: Activity, private val image: SelectedImage) {

    private var croppedImage: SelectedImage? = null

    internal fun resizeTo(bitmap: Bitmap, width: Int, height: Int): ImageCropper {

        extractScale(bitmap, width * 1F, height * 1F).also { resized ->

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
        maxHeight: Float
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

    private fun scaleTo(
        uri: Uri,
        scale: Int
    ): Bitmap? {
        return activity.contentResolver.openInputStream(uri)?.use { input ->
            val scaleOptions = BitmapFactory.Options().apply { inSampleSize = scale }
            BitmapFactory.decodeStream(input, null, scaleOptions)?.apply {
                activity.contentResolver.openOutputStream(uri)?.use { output ->
                    compress(Bitmap.CompressFormat.JPEG, 100, output)
                }
            }
        }
    }

    private fun Bitmap.correctOrientation(inputStream: InputStream): Bitmap {
        val matrix =
            try {
                when (ExifInterface(inputStream).getAttributeInt(
                    ExifInterface.TAG_ORIENTATION,
                    ExifInterface.ORIENTATION_NORMAL
                )) {
                    ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> Matrix().apply {
                        setScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_180 -> Matrix().apply { setRotate(180F) }
                    ExifInterface.ORIENTATION_FLIP_VERTICAL -> Matrix().apply {
                        setRotate(180F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_TRANSPOSE -> Matrix().apply {
                        setRotate(90F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_90 -> Matrix().apply {
                        setRotate(90F)
                    }
                    ExifInterface.ORIENTATION_TRANSVERSE -> Matrix().apply {
                        setRotate(-90F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_270 -> Matrix().apply {
                        setRotate(-90F)
                    }
                    else -> Matrix()
                }
            } catch (oops: IOException) {
                Matrix()
            }
        return Bitmap.createBitmap(this, 0, 0, this.width, this.height, matrix, true)
    }

    internal fun build(): SelectedImage {
        return croppedImage ?: throw IllegalStateException("Need to crop first dude")
    }
}