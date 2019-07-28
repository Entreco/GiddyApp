package nl.entreco.giddyapp.libmatches.data

import com.google.android.gms.tasks.Tasks
import com.google.firebase.firestore.*
import nl.entreco.giddyapp.libmatches.Match
import nl.entreco.giddyapp.libmatches.MatchResponse
import nl.entreco.giddyapp.libmatches.MatchService

internal class FbMatchService(
    private val db: FirebaseFirestore
) : MatchService {

    companion object {
        private const val MATCH_SIZE_LIMIT = 15L
    }

    private val matchesCollection = db.collection("matches")
    private val likersCollectionGroup = db.collectionGroup("likers")

    override fun match(userId: String, matches: List<Match>, done: (MatchResponse) -> Unit) {
        val batch = db.batch()

        matches.forEach { match ->
            val horseId = match.horseId
            val document = matchesCollection.document(horseId)
            val fbMatch = FbMatch(horseId, match.name, match.ref)
            batch.set(document, fbMatch, SetOptions.merge())
            batch.update(document, "likers", FieldValue.arrayUnion(userId))

            val liker = document.collection("likers").document(userId)
            batch.set(liker, FbLike(userId, horseId))
        }

        batch.commit()
            .addOnSuccessListener {
                done(MatchResponse.Ok)
            }.addOnFailureListener { err ->
                done(MatchResponse.Failed(err.localizedMessage))
            }
    }


    override fun retrieveForUser(userId: String, done: (List<Match>) -> Unit) {

        val query1 = likersCollectionGroup
            .whereEqualTo("u", userId)
            .orderBy("t", Query.Direction.DESCENDING)
            .limit(MATCH_SIZE_LIMIT)

        val query2 = matchesCollection
            .whereArrayContains("likers", userId)
            .limit(MATCH_SIZE_LIMIT)

        Tasks.whenAllSuccess<QuerySnapshot>(query1.get(), query2.get()).addOnSuccessListener { result ->
            val orders = result[0].toObjects(FbLike::class.java).map { it.horse }
            val likes = result[1].toObjects(FbMatch::class.java).map { it.horseId to Match(it.horseId, it.name, it.ref) }.toMap()
            val combined = orders.mapNotNull { key -> likes[key] }.toList()
            done(combined)
        }.addOnFailureListener {
            done(emptyList())
        }
    }
}