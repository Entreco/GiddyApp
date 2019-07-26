package nl.entreco.giddyapp.profile.header

import android.content.res.Resources
import android.net.Uri
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class ProfileHeaderViewModel @Inject constructor() : ViewModel() {

    val name = ObservableField("")
    val desc = ObservableField("")
    val image = ObservableField<Uri?>()

    fun onSignUp(navigator: ProfileHeaderNavigation) {
        navigator.onSignup()
    }

    fun onProfileChanged(profile: Profile?, resources: Resources) {
        name.set(profile?.name())
        desc.set(profile?.desc())
        image.set(profile?.image(resources))
    }

}