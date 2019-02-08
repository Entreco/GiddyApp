package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import androidx.appcompat.app.AlertDialog
import nl.entreco.giddyapp.core.onBg
import java.io.FileOutputStream
import java.text.SimpleDateFormat
import java.util.*

internal class ThirdPartyImagePicker(private val activity: Activity) : ImagePicker {

    private val imageHelper = ImageHelper(activity)
    private val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    private var fileName: String = ""

    override fun selectImage() {
        fileName = format.format(Date())
        val cameraIntent = camera(imageHelper.toInternalUri(fileName))
        val galleryIntent = gallery()

        val hasCamera = cameraIntent.resolveActivity(activity.packageManager) != null
        val hasGallery = galleryIntent.resolveActivity(activity.packageManager) != null

        if (hasCamera && hasGallery) {

            AlertDialog.Builder(activity)
                .setTitle("Choose 1 mofo")
                .setMessage("Which one fucker?")
                .setPositiveButton("Camera") { dialog, _ ->
                    dialog.dismiss()
                    launchCamera(cameraIntent)
                }
                .setNegativeButton("Gallery") { dialog, _ ->
                    dialog.dismiss()
                    launchGallery(galleryIntent)
                }
                .setCancelable(true)
                .show()

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

    private fun camera(uri: Uri): Intent {
        val cameraIntent = Intent(MediaStore.ACTION_IMAGE_CAPTURE)
        cameraIntent.flags = Intent.FLAG_GRANT_READ_URI_PERMISSION
        cameraIntent.putExtra(MediaStore.EXTRA_OUTPUT, uri)
        return Intent.createChooser(cameraIntent, "Select camera")
    }

    private fun gallery(): Intent {
        val galleryIntent = Intent(Intent.ACTION_GET_CONTENT)
        galleryIntent.type = "image/*"
        galleryIntent.putExtra(Intent.EXTRA_LOCAL_ONLY, true)
        galleryIntent.addCategory(Intent.CATEGORY_OPENABLE)
        return Intent.createChooser(galleryIntent, "Select Gallery")
    }

    private fun didComeFromGallery(req: Int, code: Int): Boolean {
        return code == Activity.RESULT_OK && req == ImagePicker.REQ_GALLERY
    }

    override fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (List<SelectedImage>) -> Unit) {

        onBg {
            if (didComeFromGallery(requestCode, resultCode)) {
                Log.i("MOFO", "CAMERA")
                imageHelper.copyUriToInternalUri(data!!.data!!, fileName)
            }

            val uri = imageHelper.toInternalUri(fileName)
            val selectedImage = SelectedImage("1L", fileName, uri)
            done(listOf(selectedImage))
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

}