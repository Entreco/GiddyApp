package nl.entreco.giddyapp.profile.profile.header

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libcore.launch.features.ProfileNavigator
import nl.entreco.giddyapp.profile.ProfileNavigation
import nl.entreco.giddyapp.profile.R
import nl.entreco.giddyapp.profile.profile.FetchProfileUsecase
import javax.inject.Inject

class ProfileHeaderViewModel @Inject constructor(
    private val fetchProfileUsecase: FetchProfileUsecase
) : ViewModel() {

    val name = ObservableField("")
    val desc = ObservableField("")
    val image = ObservableField<Uri?>()

    init {
        fetchProfileUsecase.go("header"){ profile ->
            name.set(profile.name())
            desc.set(profile.desc())
            image.set(profile.image())
        }
    }

    fun onSignUp(navigator: ProfileNavigation){
        fetchProfileUsecase.clear("header")
        navigator.onSignup()
    }

    override fun onCleared() {
        super.onCleared()
        fetchProfileUsecase.clear("header")
    }
}