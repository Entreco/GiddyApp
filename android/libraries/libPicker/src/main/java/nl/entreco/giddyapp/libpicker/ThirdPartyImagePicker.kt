package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.core.content.FileProvider
import androidx.core.graphics.BitmapCompat
import androidx.palette.graphics.Palette
import nl.entreco.giddyapp.core.HexString
import nl.entreco.giddyapp.core.onBg
import java.io.File
import java.io.FileInputStream
import java.io.FileOutputStream

internal class ThirdPartyImagePicker(private val activity: Activity) : ImagePicker {
    override fun selectImage() {

        val cameraIntent = camera()
        val galleryIntent = gallery()

        val hasCamera = cameraIntent.resolveActivity(activity.packageManager) != null
        val hasGallery = galleryIntent.resolveActivity(activity.packageManager) != null

        if (hasCamera && hasGallery) {
            // Chooser
//            val dialog = ChooserBottomSheet(frag.activity!!)
//            dialog.setContentView(R.layout.bottom_sheet_image_picker)
//            addDialogChooser(dialog, cameraIntent, galleryIntent)
//            addCancelOptions(dialog)
//            dialog.show()

            launchCamera(cameraIntent)

        } else if (hasCamera) {
            launchCamera(cameraIntent)
        } else if (hasGallery) {
            launchGallery(galleryIntent)
        } else {
            Log.d("NOPEZ", "No activity available to select images")
        }
    }

    private fun launchGallery(galleryIntent: Intent) {
        activity.startActivityForResult(galleryIntent, ImagePicker.REQ_GALLERY)
    }

    private fun launchCamera(cameraIntent: Intent) {
        activity.startActivityForResult(cameraIntent, ImagePicker.REQ_CAMERA)
    }

    private fun camera() : Intent {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(activity.filesDir))
        return Intent.createChooser(cameraIntent, "Select camera")
    }

    private fun gallery() : Intent {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
        return Intent.createChooser(galleryIntent, "Select Gallery")
    }

    private fun shouldHandle(requestCode: Int, resultCode: Int, data: Intent?) : Boolean {
        return requestCode == ImagePicker.REQ_GALLERY || requestCode == ImagePicker.REQ_CAMERA && resultCode == Activity.RESULT_OK && data != null
    }

    override fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>) -> Unit) {

        onBg {
            val images = if (shouldHandle(requestCode, resultCode, data)) {
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

        private val images: List<IntentImage> = getImages(data)
        private var localImages: List<SelectedImage> = emptyList()

        private fun getImages(data: Intent?) : List<IntentImage>{
            if(data == null) return emptyList()
            // if gallery -> copy to private folder

            // now, all images are in private folder
            return listOf(IntentImage(1L, "name", "path/to/img"))
        }

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