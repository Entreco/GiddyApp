package nl.entreco.giddyapp.core.images

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.BitmapCompat
import androidx.palette.graphics.Palette
import com.esafirm.imagepicker.features.ReturnMode
import nl.entreco.giddyapp.core.R
import nl.entreco.giddyapp.libs.horses.HexString
import nl.entreco.giddyapp.libs.horses.onBg
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

class ThirdPartyImagePicker(private val activity: Activity) : ImagePicker {
    override fun selectImage() {
        com.esafirm.imagepicker.features.ImagePicker.create(activity)
            .enableLog(true)
            .returnMode(ReturnMode.ALL)
            .folderMode(true) // folder mode (false by default)
            .toolbarFolderTitle("TODO Folder") // folder selection title
            .toolbarImageTitle("TODO Tap to select") // image selection title
            .toolbarArrowColor(Color.WHITE) // Toolbar 'up' arrow color
            .includeVideo(false) // Show video on image picker
            .single()
            .showCamera(true) // show camera or not (true by default)
            .imageFullDirectory(activity.filesDir.path)
            .imageDirectory("Create") // directory name for captured image  ("Camera" folder by default)
            .theme(R.style.ImagePickerTheme) // must inherit ef_BaseTheme. please refer to sample
            .start()
    }

    override fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>) -> Unit) {

        onBg {
            val images = if (com.esafirm.imagepicker.features.ImagePicker.shouldHandle(requestCode, resultCode, data)) {
                ImageBuilder(activity, data)
                    .copyTo(activity.filesDir)
                    .build()
            } else {
                emptyList()
            }

            done(images)
        }
    }

    override fun resize(image: SelectedImage, bmp: Bitmap?, done: (SelectedImage) -> Unit) {
        onBg {

            FileOutputStream(image.uri.path).use { fos ->
                bmp?.compress(Bitmap.CompressFormat.JPEG, 100, fos)
            }

            val scaled = ImageCropper(image)
                .resizeTo(650, 650)
                .build()

            done(scaled)
        }
    }

    internal class ImageBuilder(private val activity: Activity, data: Intent?) {

        private val images: MutableList<com.esafirm.imagepicker.model.Image> =
            com.esafirm.imagepicker.features.ImagePicker.getImages(data)
        private var localImages: List<SelectedImage> = emptyList()

        internal fun copyTo(dir: File): ImageBuilder {
            localImages = images.map { img ->

                val orig = File(img.path)
                val auth = "nl.entreco.giddyapp.imagepicker.provider"
                val uri = FileProvider.getUriForFile(activity, auth, orig)
                val to = File(dir, orig.name)

                activity.contentResolver.openInputStream(uri).use { input ->

                    // Resize here??

                    to.outputStream().use { fileOut ->
                        input?.copyTo(fileOut)
                    }
                }

                SelectedImage("${img.id}", img.name, Uri.fromFile(to))
            }

            return this
        }

        internal fun build(): List<SelectedImage> {
            return localImages
        }
    }

    internal class ImageCropper(private val image: SelectedImage) {

        private var croppedImage : SelectedImage? = null

        internal fun resizeTo(width: Int, height: Int): ImageCropper {

            val scale = extractScale(image.uri, width, height)
            val small = scale(image.uri, scale)

            val noWay = BitmapCompat.getAllocationByteCount(small!!)
            Log.i("WAAAT", "noWay: $noWay")

            val topSwatch = Palette.from(small).setRegion(0, 0, width, height / 4).generate().dominantSwatch
            val startColor = HexString.from(topSwatch?.rgb)
            val bottomSwatch = Palette.from(small).setRegion(0, 3 * height / 4, width, height).generate().dominantSwatch
            val endColor = HexString.from(bottomSwatch?.rgb)

            croppedImage = image.copy(startColor = startColor, endColor =  endColor)

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
}