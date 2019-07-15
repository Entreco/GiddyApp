package nl.entreco.giddyapp.profile.profile.header

import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.user.SigninMethod
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.profile.profile.FetchProfileUsecase
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class ProfileHeaderViewModel @Inject constructor(
    private val authenticator: Authenticator,
    fetchProfileUsecase: FetchProfileUsecase
) : ViewModel() {

    val currentProfile = ObservableField<Profile>()

    init {
        fetchProfileUsecase.go { profile ->
            currentProfile.set(profile)
        }
    }

    fun action() {
        if(currentProfile.get()?.user is User.Anomymous) {
            authenticator.link(SigninMethod.Google("some token")) { user ->
                currentProfile.set(Profile(user))
            }
        }
    }
}