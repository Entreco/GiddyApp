package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.DeleteResponse
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject

internal class FbUserService @Inject constructor(
    db: FirebaseFirestore,
    private val auth: FirebaseAuth
) : UserService {

    private val userCollection = db.collection("users")
    private val userMapper by lazy { UserMapper() }

    override fun create(name: String, done: (User) -> Unit) {
        val uuid = auth.currentUser?.uid
        if (uuid.isNullOrBlank()) done(User.Error("No user signed in"))
        else {
            userCollection.document(uuid)
                .set(FbUser(name), SetOptions.merge())
                .addOnSuccessListener {
                    retrieve(done)
                }.addOnFailureListener { err ->
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
            userCollection.document(uuid)
                .get()
                .addOnSuccessListener { snap ->
                    val user = snap.toObject(FbUser::class.java)
                    done(userMapper.toUser(uuid, user))
                }.addOnFailureListener { err ->
                    done(User.Error(err.localizedMessage))
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