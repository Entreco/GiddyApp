package nl.entreco.giddyapp.libpicker.picker

import android.app.Activity
import android.net.Uri
import android.util.Log
import androidx.core.content.FileProvider
import java.io.File

internal class ImageHelper(private val activity: Activity) {

    companion object {
        private const val ext = ".jpg"
        private const val claimsSubDir = "creator"
        private const val BYTES_IN_MEGABYTE = (1024.0 * 1000)
    }

    fun toInternalUri(fileName: String): Uri {
        val photoFile = toInternalFile(fileName)
        return toContentUri(photoFile)
    }

    fun deleteImageAtLocation(fileName: String) {
        val photoToDelete = toInternalFile(fileName)
        if (photoToDelete.exists()) photoToDelete.delete()
    }

    fun copyUriToInternalUri(from: Uri, to: String) {
        try {
            val photoFile = toInternalFile(to)
            activity.contentResolver.openInputStream(from).use { input ->
                photoFile.outputStream().use { fileOut ->
                    input!!.copyTo(fileOut)
                }
            }
        } catch (err: Throwable) {
            Log.d("DOH", "Unable to copy Uri to internal file structure")
        }
    }

    private fun toContentUri(it: File) =
        FileProvider.getUriForFile(activity, "nl.entreco.giddyapp.libpicker.provider", it)

    private fun toInternalFile(fileName: String, extension: String = ext): File {
        return File(getImageDirectory(), "$fileName$extension")
    }

    private fun getImageDirectory() = File(
        activity.filesDir,
        claimsSubDir
    ).apply { mkdirs() }
}
