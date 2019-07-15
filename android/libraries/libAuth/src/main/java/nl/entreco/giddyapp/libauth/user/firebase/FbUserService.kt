package nl.entreco.giddyapp.libauth.user.firebase

import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.SetOptions
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libauth.user.User
import javax.inject.Inject

internal class FbUserService @Inject constructor(
    db: FirebaseFirestore
) : UserService {

    private val userCollection = db.collection("users")

    override fun create(uuid: String, userData: User, done: (User) -> Unit) {
        userCollection.document(uuid).set(
            FbUser(likes = userData.likes, dislikes = userData.dislikes),
            SetOptions.merge()
        )
            .addOnSuccessListener { snap ->
                done(User(uuid, userData.likes, userData.dislikes))
            }
    }

    override fun retrieveAndDelete(uuid: String, done: (User) -> Unit) {
        userCollection.document(uuid).get()
            .addOnSuccessListener { snapshot ->
                val old = snapshot.toObject(FbUser::class.java)
                if(old != null){
                    val deleted = User(old.name, old.likes, old.dislikes)
                    userCollection.document(uuid).delete().addOnSuccessListener {
                        done(deleted)
                    }
                } else {
                    done(User("", emptyList(), emptyList()))
                }
            }
            .addOnFailureListener {
                done(User("", emptyList(), emptyList()))
            }
    }
}