package nl.entreco.giddyapp.libpicker.picker

import android.app.Activity
import android.content.Intent
import android.graphics.Bitmap
import android.net.Uri
import android.provider.MediaStore
import android.util.Log
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libpicker.ImagePicker
import nl.entreco.giddyapp.libpicker.ImagePickerResult
import nl.entreco.giddyapp.libpicker.SelectedImage
import nl.entreco.giddyapp.libpicker.moderator.ApplyModerationRequest
import nl.entreco.giddyapp.libpicker.moderator.ApplyModerationUsecase
import nl.entreco.giddyapp.libpicker.moderator.Moderation
import java.text.SimpleDateFormat
import java.util.*

internal class ThirdPartyImagePicker(
    private val activity: Activity,
    private val imageHelper: ImageHelper,
    private val imageCropper: ImageCropper,
    private val moderationUsecase: ApplyModerationUsecase
) :
    ImagePicker {

    private val required = listOf("horse")
    private val blocked = listOf("porn", "violence", "nudity", "profanity")
    private val format = SimpleDateFormat("yyyyMMdd_HHmmss", Locale.getDefault())
    private var fileName: String = ""

    override fun selectImage(fromCamera: Boolean) {
        fileName = format.format(Date())
        val cameraIntent = camera(imageHelper.toInternalUri(fileName))
        val galleryIntent = gallery()

        val hasCamera = cameraIntent.resolveActivity(activity.packageManager) != null
        val hasGallery = galleryIntent.resolveActivity(activity.packageManager) != null

        if (hasCamera && fromCamera) {
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

    private fun didCancel(result: Int): Boolean {
        return result != Activity.RESULT_OK
    }

    private fun didComeFromGallery(request: Int, result: Int): Boolean {
        return result == Activity.RESULT_OK && request == ImagePicker.REQ_GALLERY
    }

    override fun get(requestCode: Int, resultCode: Int, data: Intent?, done: (ImagePickerResult) -> Unit) {
        if (didCancel(resultCode)) return

        onBg {
            if (didComeFromGallery(requestCode, resultCode)) {
                imageHelper.copyUriToInternalUri(data!!.data!!, fileName)
            }

            val uri = imageHelper.toInternalUri(fileName)
            val selectedImage = SelectedImage("1L", fileName, uri)

            moderationUsecase.go(ApplyModerationRequest(activity, uri, required, blocked)) {
                when (it.mod) {
                    is Moderation.Allowed -> done(ImagePickerResult.Success(listOf(selectedImage)))
                    is Moderation.Rejected -> done(ImagePickerResult.Failed(it.mod))
                }
            }
        }
    }

    override fun resize(image: SelectedImage, bmp: Bitmap, done: (SelectedImage) -> Unit) {
        onBg {

            val scaled = imageCropper
                .resizeTo(bmp, 1050, 1050, image)
                .build()
            done(scaled)
        }
    }
}