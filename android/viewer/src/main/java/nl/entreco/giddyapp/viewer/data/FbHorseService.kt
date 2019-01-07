package nl.entreco.giddyapp.viewer.data

import android.net.Uri
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

    override fun fetch(id: String, done: (Horse?) -> Unit) {
        val docRef = collection.document(id)
        docRef.get().addOnSuccessListener { documentSnapshot ->
            val fbHorse = documentSnapshot.toObject(FbHorse::class.java)
            if (fbHorse != null) {
                done(mapper.map(fbHorse, "${documentSnapshot.id}.jpg"))
            } else done(null)
        }.addOnFailureListener { exception ->
            done(null)
        }
    }

    override fun image(ref: String, done: (Uri) -> Unit) {
        storage.getReference(ref).downloadUrl.addOnSuccessListener {
            done(it)
        }
    }
}