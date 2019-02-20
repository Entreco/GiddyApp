package nl.entreco.giddyapp.libpicker

import android.util.Log
import androidx.exifinterface.media.ExifInterface
import java.io.File
import java.io.InputStream
import java.util.*

internal class ExifDataCopier {

    fun copyExif(oriStream: InputStream, file: File) {
        try {
            val oldExif = ExifInterface(oriStream)
            val newExif = ExifInterface(file.path)

            val attributes = Arrays.asList(
                "FNumber",
                "ExposureTime",
                "ISOSpeedRatings",
                "GPSAltitude",
                "GPSAltitudeRef",
                "FocalLength",
                "GPSDateStamp",
                "WhiteBalance",
                "GPSProcessingMethod",
                "GPSTimeStamp",
                "DateTime",
                "Flash",
                "GPSLatitude",
                "GPSLatitudeRef",
                "GPSLongitude",
                "GPSLongitudeRef",
                "Make",
                "Model",
                "Orientation"
            )
            for (attribute in attributes) {
                setIfNotNull(oldExif, newExif, attribute)
            }

            newExif.saveAttributes()

        } catch (ex: Exception) {
            Log.e("ExifDataCopier", "Error preserving Exif data on selected image: $ex")
        }
    }

    private fun setIfNotNull(oldExif: ExifInterface, newExif: ExifInterface, attribute: String) {
        oldExif.getAttribute(attribute)?.let { old ->
            newExif.setAttribute(attribute, old)
        }
    }
}