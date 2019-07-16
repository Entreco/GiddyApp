package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libauth.user.UserLike
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
                    val user = snap.toObject(FbUser::class.java)
                    if (user != null) {
                        userCollection.document(uuid).collection("likes").get().addOnCompleteListener { task1 ->
                            val likes = task1.result?.map { res ->
                                res.toObject(FbUserLike::class.java)
                            } ?: emptyList()

                            userCollection.document(uuid).collection("dislikes").get().addOnCompleteListener { task2 ->
                                val dislikes = task2.result?.map { res ->
                                    res.toObject(FbUserLike::class.java)
                                } ?: emptyList()

                                done(userMapper.toUser(user, likes, dislikes))
                            }
                        }
                    } else {
                        done(User.Error("Empty User"))
                    }

                }.addOnFailureListener { err ->
                    done(User.Error(err.localizedMessage))
                }
        }
    }

    override fun rate(likes: List<UserLike>, dislikes: List<UserLike>, done: () -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done()
        else {
            val batch = db.batch()
            val user = userCollection.document(uuid)

            val l = user.collection("likes")
            likes.forEach { like ->
                val fbLike = FbUserLike(like.horseName, like.horseId, like.horseRef)
                batch.set(l.document(like.horseId), fbLike)
            }

            val d = user.collection("dislikes")
            dislikes.forEach { dislike ->
                val fbDislike = FbUserLike(dislike.horseName, dislike.horseId, dislike.horseRef)
                batch.set(d.document(dislike.horseId), fbDislike)
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