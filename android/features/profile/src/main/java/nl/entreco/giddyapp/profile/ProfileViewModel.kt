package nl.entreco.giddyapp.profile

import android.content.Context
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.profile.profile.FetchProfileUsecase
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val fetchProfileUsecase: FetchProfileUsecase
) : ViewModel() {

    val currentProfile = ObservableField<Profile>()

    private val state = MutableLiveData<Profile>()
    fun state(): LiveData<Profile> = state

    init {
        refreshProfile()
    }

    fun refreshProfile() {
        fetchProfileUsecase.go { profile ->
            currentProfile.set(profile)
            state.postValue(profile)
        }
    }

    fun version(context: Context): String =
        context.applicationContext.packageManager.getPackageInfo(context.packageName, 0).versionName

    override fun onCleared() {
        super.onCleared()
        fetchProfileUsecase.clear()
    }
}