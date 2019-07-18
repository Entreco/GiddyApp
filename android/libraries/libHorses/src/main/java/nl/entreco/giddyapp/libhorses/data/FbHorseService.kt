package nl.entreco.giddyapp.libhorses.data

import android.net.Uri
import android.util.Log
import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.QuerySnapshot
import com.google.firebase.storage.FirebaseStorage
import nl.entreco.giddyapp.libcore.HexString
import nl.entreco.giddyapp.libhorses.*
import nl.entreco.giddyapp.libhorses.fetch.FilterOptions
import javax.inject.Inject
import kotlin.math.max
import kotlin.random.Random

internal class FbHorseService @Inject constructor(
    private val db: FirebaseFirestore,
    private val storage: FirebaseStorage
) : HorseService {

    private val horseCollection = db.collection("horses")
    private val mapper by lazy { HorseMapper() }

    override fun retrieve(ids: List<String>, filterOptions: FilterOptions, done: (List<Horse>) -> Unit) {
        Log.i("FILTER", "filter: $filterOptions")

        val inc = filterOptions.includes()
        val exc = filterOptions.excludes()

        Log.i("FILTER", "includes: $inc")
        Log.i("FILTER", "excludes: $exc")

        horseCollection.get().addOnSuccessListener { snapshot ->
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
        userId: String,
        horseId: String,
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
        val document = horseCollection.document(horseId)

        // 1) Upload Image
        upload(userId, document.id, image) { url ->
            // 2) Set Image Details
            val horse = mapper.create(name, description, gender, price, category, level, startColor, endColor, url)
            document.set(horse).addOnSuccessListener {
                done(document.id)
            }.addOnFailureListener {
                done(it.localizedMessage)
            }
        }
    }

    private fun mapResults(
        ids: List<String>,
        query: QuerySnapshot
    ): List<Horse> {
        val nonNull = ids.filter { it.isNotBlank() }.fetchOrNotFound(query)
        val randomRange = ids.size - nonNull.size
        val randomStart = Random.nextInt(max(query.size() - randomRange, 1))
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

    private fun upload(userId: String, documentId: String, image: Uri, done: (String) -> Unit) {
        val reference = storage.reference
        val imageRef = reference.child(userId).child("$documentId.jpg")
        imageRef.putFile(image).addOnSuccessListener {
            done(imageRef.path)
        }.addOnFailureListener {
            done("")
        }
    }

    override fun image(ref: String, done: (DownloadUrl) -> Unit) {
        val reference = storage.reference.child(ref)
        reference.downloadUrl.addOnSuccessListener { uri ->
            done(DownloadUrl.Ok(uri))
        }.addOnFailureListener { err ->
            done(DownloadUrl.Err(err.localizedMessage))
        }
    }

    override fun rate(likes: List<String>, dislikes: List<String>, done: (HorseRating) -> Unit) {

        val tasks = likes.map { horseCollection.document(it) }.map { doc ->
            doc.update("likes", FieldValue.increment(1))
        }.union(dislikes.map { horseCollection.document(it) }.map { doc ->
            doc.update("dislikes", FieldValue.increment(1))
        })

        Tasks.whenAllComplete(tasks).addOnCompleteListener { task ->
            val rating = when (task.isSuccessful) {
                true -> HorseRating.Ok
                else -> HorseRating.Failed(task.exception?.localizedMessage ?: "Error while rating")
            }
            done(rating)
        }
    }
}