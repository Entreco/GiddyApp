package nl.entreco.giddyapp.viewer.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import nl.entreco.giddyapp.viewer.domain.Horse
import nl.entreco.giddyapp.viewer.domain.HorseService
import javax.inject.Inject
import kotlin.random.Random

internal class FbHorseService @Inject constructor(
    db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : HorseService {

    private val collection = db.collection("horses")
    private val mapper by lazy { HorseMapper() }

    override fun fetch(ids: List<String?>, done: (List<Horse>) -> Unit) {
        collection.get().addOnSuccessListener { query ->
            val horses = when {
                query.isEmpty -> emptyList()
                else -> mapResults(ids.size, query)
            }
            done(horses)
        }.addOnFailureListener {
            done(emptyList())
        }
    }

    private fun mapResults(
        to: Int,
        query: QuerySnapshot
    ): List<Horse> {
        val random = Random.nextInt(query.size())
        return (random until random + to).mapNotNull { index ->
            val idx = index % query.size()
            if (idx < query.size()) {
                val doc = query.documents[idx]
                val fbHorse = doc.toObject(FbHorse::class.java)
                if (fbHorse != null) {
                    mapper.map(fbHorse, doc.id)
                } else {
                    Horse.error()
                }
            } else {
                Horse.none()
            }
        }
    }

    private fun random(): String {
        // create a big random number - maximum is ffffff (hex) = 16777215 (dez)
        val nextInt = Random.nextInt(0xffffff + 1)
        // format it as hexadecimal string (with hashtag and leading zeros)
        return String.format("#%06x", nextInt)
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