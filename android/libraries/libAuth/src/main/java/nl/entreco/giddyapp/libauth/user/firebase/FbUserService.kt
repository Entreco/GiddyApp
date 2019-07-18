package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libauth.user.UserRating
import javax.inject.Inject

internal class FbUserService @Inject constructor(
    private val db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {

    private val userCollection = db.collection("users")
    private val userMapper by lazy { UserMapper() }

    override fun create(name: String, likes: List<UserLike>, done: (User) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(User.Error("No user signed in"))
        else {
            userCollection.document(uuid)
                .set(FbUser(name), SetOptions.merge())
                .continueWithTask {
                    userCollection.document(uuid).collection("likes")
                        .add(likes.map {
                            FbUserLike(it.horseName, it.horseId, it.horseRef)
                        })
                }
                .addOnSuccessListener {
                    retrieve(done)
                }
                .addOnFailureListener { err ->
                    done(User.Error(err.localizedMessage))
                }
        }
    }

    override fun createHorse(
        name: String,
        done: (UserHorse) -> Unit
    ) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(UserHorse.Err("No user logged in"))
        else {
            val horseRef = userCollection.document(uuid).collection("horses").document()
            horseRef.set(FbUserHorse(name)).addOnSuccessListener {
                done(UserHorse.Ok(uuid, horseRef.id))
            }.addOnFailureListener {
                done(UserHorse.Err("Unable to create horse"))
            }
        }
    }

    override fun retrieve(done: (User) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(User.Error("No user signed in"))
        else {
            user { fbUser ->
                userLikes { fbLikes ->
                    done(userMapper.toUser(fbUser, fbLikes))
                }
            }
        }
    }

    private fun user(done: (FbUser?) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(null)
        else {
            userCollection.document(uuid)
                .get()
                .addOnSuccessListener { snap ->
                    val user = snap.toObject(FbUser::class.java)
                    done(user)
                }.addOnFailureListener { err ->
                    done(null)
                }
        }
    }

    private fun userLikes(done: (List<FbUserLike>) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(emptyList())
        else {
            userCollection.document(uuid)
                .collection("likes")
                .get()
                .addOnSuccessListener { snap ->
                    val likes = snap.toObjects(FbUserLike::class.java)
                    done(likes)
                }.addOnFailureListener {
                    done(emptyList())
                }
        }
    }

    override fun rate(likes: List<UserLike>, dislikes: List<UserLike>, done: (UserRating) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(UserRating.Err)
        else {
            val batch = db.batch()
            val user = userCollection.document(uuid)

            val likesCollection = user.collection("likes")
            likes.forEach { like ->
                val fbLike = FbUserLike(like.horseName, like.horseId, like.horseRef)
                batch.set(likesCollection.document(like.horseId), fbLike)
            }

            batch.commit().addOnCompleteListener { task ->

                val result = when (task.isSuccessful) {
                    true -> UserRating.Ok
                    else -> UserRating.Err
                }

                done(result)
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