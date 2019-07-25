package nl.entreco.giddyapp.libmatches.data

import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import nl.entreco.giddyapp.libmatches.Match
import nl.entreco.giddyapp.libmatches.MatchResponse
import nl.entreco.giddyapp.libmatches.MatchService

internal class FbMatchService(
    private val db: FirebaseFirestore
) : MatchService {

    private val matchesCollection = db.collection("matches")

    override fun match(userId: String, matches: List<Match>, done: (MatchResponse) -> Unit) {
        val batch = db.batch()

        matches.forEach { match ->
            val horseId = match.horseId
            val document = matchesCollection.document(horseId)
            val fbMatch = FbMatch(horseId, match.name, match.ref)
            batch.set(document, fbMatch, SetOptions.merge())
            batch.update(document, "likers", FieldValue.arrayUnion(userId))
        }

        batch.commit()
            .addOnSuccessListener {
                done(MatchResponse.Ok)
            }.addOnFailureListener { err ->
                done(MatchResponse.Failed(err.localizedMessage))
            }
    }

    override fun retrieveForUser(userId: String, done: (List<Match>) -> Unit) {

        matchesCollection.whereArrayContains("likers", userId).get()
            .addOnSuccessListener { snap ->
                val matches = snap.toObjects(FbMatch::class.java).mapNotNull { Match(it.horseId, it.name, it.ref) }
                done(matches)
            }.addOnFailureListener { err ->
                done(emptyList())
            }
    }
}