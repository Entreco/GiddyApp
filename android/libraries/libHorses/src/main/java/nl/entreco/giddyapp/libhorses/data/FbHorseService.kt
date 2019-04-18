package nl.entreco.giddyapp.libhorses.data

import android.net.Uri
import android.util.Log
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.*
import nl.entreco.giddyapp.libhorses.fetch.FilterOptions
import javax.inject.Inject
import kotlin.random.Random

internal class FbHorseService @Inject constructor(
    db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : HorseService {

    private val collection = db.collection("horses")
    private val mapper by lazy { HorseMapper() }

    override fun fetch(ids: List<String>, filterOptions: FilterOptions, done: (List<Horse>) -> Unit) {
        Log.i("FILTER", "filter: $filterOptions")

        val inc = filterOptions.includes()
        val exc = filterOptions.excludes()

        Log.i("FILTER", "includes: $inc")
        Log.i("FILTER", "excludes: $exc")
        collection.limit(ids.size.toLong()).get().addOnSuccessListener { snapshot ->
            val horses = when {
                snapshot.isEmpty -> emptyList()
                else -> mapResults(ids, snapshot)
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
        price: HorsePrice,
        category: HorseCategory,
        level: HorseLevel,
        image: Uri,
        startColor: HexString,
        endColor: HexString,
        done: (String) -> Unit
    ) {
        val document = collection.document()

        // 1) Upload Image
        upload(document.id, image) {
            // 2) Set Image Details
            val horse = mapper.create(name, description, gender, price, category, level, startColor, endColor)
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

    private fun upload(documentId: String, image: Uri, done: (String?) -> Unit) {
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