package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.DocumentReference
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.model.value.ReferenceValue
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject

internal class FbUserService @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {

    private val userCollection = db.collection("users")
    private val userMapper by lazy { UserMapper() }

    override fun create(userData: User, done: (User) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(User.Error("No user signed in"))
        else {
            val fbUser = userMapper.toApiData(auth.currentUser?.displayName ?: "No Name", userData)
            userCollection.document(uuid).set(fbUser, SetOptions.merge())
                .addOnSuccessListener {
                    retrieve(done)
                }
                .addOnFailureListener { err ->
                    done(User.Error(err.localizedMessage))
                }
        }
    }

    override fun retrieve(done: (User) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(User.Error("No user signed in"))
        else {
            userCollection.document(uuid)
                .get()
                .addOnSuccessListener { snap ->
                    done(userMapper.toPoko(snap.toObject(FbUser::class.java)))
                }.addOnFailureListener { err ->
                    done(User.Error(err.localizedMessage))
                }
        }
    }

    override fun rate(likes: List<String>, dislikes: List<String>, done: () -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done()
        else {
            val batch = db.batch()
            val user = userCollection.document(uuid)

            val l = user.collection("likes")
            likes.map { FbLike(db.document("horses/$it")) }.forEach {
                batch.set(l.document(), it)
            }

            val d = user.collection("dislikes")
            dislikes.map{ FbLike(db.document("horses/$it"))}.forEach {
                batch.set(d.document(), it)
            }

            batch.commit().addOnCompleteListener {
                done()
            }
        }
    }

    override fun delete(done: (DeleteResponse) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(DeleteResponse.Err("No user signed in"))
        else {
            userCollection.document(uuid)
                .delete()
                .addOnSuccessListener { done(DeleteResponse.Ok(uuid)) }
                .addOnFailureListener { err -> done(DeleteResponse.Err(err.localizedMessage)) }
        }
    }
}