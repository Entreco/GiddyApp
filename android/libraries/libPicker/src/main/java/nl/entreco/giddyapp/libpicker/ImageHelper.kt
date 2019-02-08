package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Matrix
import android.net.Uri
import android.util.Base64
import android.util.Log
import androidx.core.content.FileProvider
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.IOException
import kotlin.math.sqrt

class ImageHelper(private val activity: Activity) {

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

    fun isValidFileLocation(fileName: String): Boolean {
        return toInternalFile(fileName).exists()
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

    fun sizeInMegaBytes(fileName: String): Double {
        val photoFile = toInternalFile(fileName)
        return photoFile.length() / BYTES_IN_MEGABYTE
    }

    fun toBase64(fileName: String, maxSizeInMb: Double, numberOfImages: Int): String {
        resizeImageIfLimitExceeded(fileName, maxSizeInMb, numberOfImages)
        return encodeBase64(fileName)
    }

    private fun encodeBase64(fileName: String): String {
        val photoUri = Uri.parse(fileName)
        return activity.contentResolver.openInputStream(photoUri).use { input ->
            Base64.encodeToString(input?.readBytes(), Base64.DEFAULT or Base64.NO_WRAP)
        }
    }

    private fun resizeImageIfLimitExceeded(fileName: String, maxSizeInMb: Double,
                                           numberOfImages: Int) {

        val internalFileName = fileName.split("/").last().split(".").first()
        val max = maxSizeInMb / numberOfImages
        val size = sizeInMegaBytes(internalFileName)

        val bmp = calculateOptimalSize(internalFileName, size, max).also {
            correctOrientation(it, toInternalFile(internalFileName).name)
        }

        saveResizedImage(fileName, bmp)
    }

    private fun calculateOptimalSize(internalFileName: String, fileSizeInMegaBytes: Double,
                                     maxSizeInBytes: Double): Bitmap {
        val file = toInternalFile(internalFileName)
        val options = BitmapFactory.Options()
        options.inJustDecodeBounds = true
        BitmapFactory.decodeFile(file.absolutePath, options)
        options.inSampleSize = calculateInSampleSize(options, fileSizeInMegaBytes, maxSizeInBytes)
        options.inJustDecodeBounds = false
        return BitmapFactory.decodeFile(file.absolutePath, options)
    }

    private fun correctOrientation(bitmap: Bitmap, fileName: String): Bitmap {
        val matrix =
            try {
                when (ExifInterface(fileName).getAttributeInt(ExifInterface.TAG_ORIENTATION, ExifInterface.ORIENTATION_NORMAL)) {
                    ExifInterface.ORIENTATION_FLIP_HORIZONTAL -> Matrix().apply {
                        setScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_180      -> Matrix().apply { setRotate(180F) }
                    ExifInterface.ORIENTATION_FLIP_VERTICAL   -> Matrix().apply {
                        setRotate(180F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_TRANSPOSE       -> Matrix().apply {
                        setRotate(90F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_90       -> Matrix().apply {
                        setRotate(90F)
                    }
                    ExifInterface.ORIENTATION_TRANSVERSE      -> Matrix().apply {
                        setRotate(-90F)
                        postScale(-1F, 1F)
                    }
                    ExifInterface.ORIENTATION_ROTATE_270      -> Matrix().apply {
                        setRotate(-90F)
                    }
                    else                                      -> Matrix()
                }
            } catch (oops: IOException) {
                Matrix()
            }
        return Bitmap.createBitmap(bitmap, 0, 0, bitmap.width, bitmap.height, matrix, true)
    }

    private fun saveResizedImage(fileName: String, bmp: Bitmap) {
        activity.contentResolver.openOutputStream(Uri.parse(fileName)).use { output ->
            bmp.compress(Bitmap.CompressFormat.JPEG, 90, output)
        }
    }

    private fun calculateInSampleSize(options: BitmapFactory.Options, fileSize: Double,
                                      maxSize: Double): Int {
        var inSampleSize = 1

        if (fileSize > maxSize) {
            val factor = sqrt(maxSize / fileSize)
            val width = options.outWidth * factor
            val height = options.outHeight * factor

            while (options.outWidth / inSampleSize >= width && options.outHeight / inSampleSize >= height) {
                inSampleSize *= 2
            }
        }

        return inSampleSize
    }

    fun toExtension(fileName: String): String {
        return fileName.split(".").last().toUpperCase()
    }

    fun clearClaimsImages() {
        getImageDirectory().listFiles().forEach {
            it.delete()
        }
    }

    fun retrieveClaimImages(): List<Uri> {
        val files = getImageDirectory().listFiles().sortedBy { it.lastModified() }
        return files.map { toContentUri(it) }
    }

    private fun toContentUri(it: File) = FileProvider.getUriForFile(activity, "nl.entreco.giddyapp.libpicker.provider", it)

    private fun toInternalFile(fileName: String, extension: String = ext): File {
        return File(getImageDirectory(), "$fileName$extension")
    }

    private fun getImageDirectory() = File(activity.filesDir, claimsSubDir).apply { mkdirs() }
}
