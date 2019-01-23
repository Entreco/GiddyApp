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

    override fun fetch(ids: List<String>, done: (List<Horse>) -> Unit) {
        collection.get().addOnSuccessListener { query ->
            val horses = when {
                query.isEmpty -> emptyList()
                else -> mapResults(ids, query)
            }
            done(horses)
        }.addOnFailureListener {
            done(emptyList())
        }
    }

    private fun mapResults(
        ids: List<String>,
        query: QuerySnapshot
    ): List<Horse> {
        val nonNull = ids.filter { it.isNotBlank() }.fetchOrNotFound(query)
        val randomRange = ids.size - nonNull.size
        val randomStart = Random.nextInt(randomRange)
        val random = (randomStart until randomStart + randomRange).map { index ->
            index % query.size()
        }.fetchOrError(query)

        return (nonNull + random).distinct()
    }

    private fun Iterable<String>.fetchOrNotFound(query: QuerySnapshot): List<Horse> {
        return map { id ->
            val doc = query.documents.firstOrNull { it.id == id }
            val fbHorse = doc?.toObject(FbHorse::class.java)
            if (fbHorse != null) {
                mapper.map(fbHorse, doc.id)
            } else {
                Horse.notFound(id)
            }
        }
    }

    private fun Iterable<Int>.fetchOrError(query: QuerySnapshot): List<Horse> {
        return map { idx ->
            val doc = query.documents[idx]
            val fbHorse = doc?.toObject(FbHorse::class.java)
            if (fbHorse != null) {
                mapper.map(fbHorse, doc.id)
            } else {
                Horse.error()
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