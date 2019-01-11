package nl.entreco.giddyapp.viewer.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.storage.FirebaseStorage
import nl.entreco.giddyapp.viewer.Horse
import nl.entreco.giddyapp.viewer.HorseService
import javax.inject.Inject

internal class FbHorseService @Inject constructor(
    db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : HorseService {

    private val collection = db.collection("horses")
    private val mapper by lazy { HorseMapper() }

    override fun fetch(ids: List<String?>, done: (List<Horse>) -> Unit) {
        val docRef = collection.document(ids[0] ?: "")
        docRef.get().addOnSuccessListener { querySnapshot ->
            querySnapshot.toObject(FbHorse::class.java)?.let { horse ->
                done(listOf(mapper.map(horse, querySnapshot.id), Horse("Remco", "#ff6600", "#ffff00", "nopez")))
            }
        }.addOnFailureListener { exception ->
            done(emptyList())
        }
    }

    override fun image(ref: String, done: (Uri) -> Unit) {
        val reference = storage.reference.child(ref)
        reference.downloadUrl.addOnSuccessListener {
            done(it)
        }.addOnFailureListener {
            Log.i("NOOOOOOH", "e: $it")
        }
    }
}