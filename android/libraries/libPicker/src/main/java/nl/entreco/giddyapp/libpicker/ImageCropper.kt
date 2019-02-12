package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import androidx.exifinterface.media.ExifInterface
import androidx.palette.graphics.Palette
import nl.entreco.giddyapp.core.HexString
import java.io.IOException
import java.io.InputStream

internal class ImageCropper(private val activity: Activity, private val image: SelectedImage) {

    private var croppedImage: SelectedImage? = null

    internal fun resizeTo(width: Int, height: Int): ImageCropper {

        val scale = extractScale(image.uri, width, height)
        scaleTo(image.uri, scale)?.also { resized ->

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
        uri: Uri,
        width: Int,
        height: Int
    ): Int {
        return activity.contentResolver.openInputStream(uri).use { input ->
            val opts = BitmapFactory.Options().apply { inJustDecodeBounds = true }
            BitmapFactory.decodeStream(input, null, opts)
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

    private fun scaleTo(
        uri: Uri,
        scale: Int
    ): Bitmap? {
        return activity.contentResolver.openInputStream(uri)?.use { input ->
            val scaleOptions = BitmapFactory.Options().apply { inSampleSize = scale }
            BitmapFactory.decodeStream(input, null, scaleOptions)?.apply {
                activity.contentResolver.openOutputStream(uri)?.use { output ->
                    correctOrientation(input).compress(Bitmap.CompressFormat.JPEG, 100, output)
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