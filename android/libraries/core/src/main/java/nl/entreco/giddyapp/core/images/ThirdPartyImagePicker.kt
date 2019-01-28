package nl.entreco.giddyapp.core.images

import android.app.Activity
import android.content.Intent
import android.graphics.BitmapFactory
import android.graphics.Color
import android.net.Uri
import androidx.core.content.FileProvider
import androidx.palette.graphics.Palette
import com.esafirm.imagepicker.features.ReturnMode
import nl.entreco.giddyapp.core.R
import nl.entreco.giddyapp.libs.horses.onBg
import java.io.File
import kotlin.math.min

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
                    .resizeTo(650, 650)
                    .build()
            } else {
                emptyList()
            }

            done(images)
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
                    to.outputStream().use { fileOut ->
                        input!!.copyTo(fileOut)
                    }
                }

                SelectedImage("${img.id}", img.name, Uri.fromFile(to))
            }

            return this
        }

        internal fun resizeTo(width: Int, height: Int): ImageBuilder {
            localImages = localImages.map {
                val boundsOnlyOptions = BitmapFactory.Options().apply { inJustDecodeBounds = true }
                BitmapFactory.decodeFile(it.uri.path, boundsOnlyOptions)
                var w = boundsOnlyOptions.outWidth
                var h = boundsOnlyOptions.outHeight

                var scale = 1
                while(true) {
                    if(w / 2 < width || h / 2 < height)
                        break;
                    w /= 2;
                    h /= 2;
                    scale *= 2;
                }

                val scaleOptions = BitmapFactory.Options().apply { inSampleSize = scale }
                val bitmap = BitmapFactory.decodeStream(activity.contentResolver.openInputStream(it.uri), null, scaleOptions)

                val topSwatch = Palette.from(bitmap).setRegion(0, 0, width, height / 4).generate().dominantSwatch
                val startColor = if (topSwatch != null) Integer.toHexString(topSwatch.rgb) else "ffFFFFFF"
                val bottomSwatch = Palette.from(bitmap).setRegion(0, 3 * height / 4, width, height).generate().dominantSwatch
                val endColor = if (bottomSwatch != null) Integer.toHexString(bottomSwatch.rgb) else "ffFFFFFF"

                it.copy(startColor = "#$startColor", endColor = "#$endColor")
            }
            return this
        }

        internal fun build(): List<SelectedImage> {
            return localImages
        }
    }
}