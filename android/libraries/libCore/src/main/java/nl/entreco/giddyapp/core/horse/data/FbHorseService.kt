package nl.entreco.giddyapp.core.horse.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import nl.entreco.giddyapp.libs.horses.HexString
import nl.entreco.giddyapp.libs.horses.Horse
import nl.entreco.giddyapp.libs.horses.HorseGender
import nl.entreco.giddyapp.libs.horses.HorseService
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

    override fun create(
        name: String,
        description: String,
        gender: HorseGender,
        image: Uri,
        startColor: HexString,
        endColor: HexString,
        done: (String) -> Unit
    ) {
        val document = collection.document()

        // 1) Upload Image
        upload(document.id, image){
            // 2) Set Image Details
            val horse = mapper.create(name, description, gender, startColor, endColor)
            document.set(horse).addOnSuccessListener {
                // TODO: Extract extension from Image
                done(document.id)
            }
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

    private fun upload(documentId: String, image: Uri, done:(String?)->Unit){
        val reference = storage.reference
        val imageRef = reference.child("$documentId.jpg")
        imageRef.putFile(image).addOnSuccessListener {
            done(imageRef.name)
        }.addOnFailureListener {
            done(null)
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