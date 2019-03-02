package nl.entreco.giddyapp.libpicker.labeller

import android.content.Context
import android.net.Uri
import com.google.firebase.ml.vision.common.FirebaseVisionImage
import com.google.firebase.ml.vision.label.FirebaseVisionImageLabeler
import nl.entreco.giddyapp.libpicker.ImageLabeller

internal class MlKitImageLabeller(private val fbLabeller: FirebaseVisionImageLabeler) :
    ImageLabeller {

    override fun getLabels(context: Context, uri: Uri, done: (List<String>) -> Unit) {
        val image = FirebaseVisionImage.fromFilePath(context, uri)
        fbLabeller.processImage(image)
            .addOnSuccessListener { results -> done(results.map { it.text }) }
            .addOnFailureListener { done(emptyList()) }
    }
}