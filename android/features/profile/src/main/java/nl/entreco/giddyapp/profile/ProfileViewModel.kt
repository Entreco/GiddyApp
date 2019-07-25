package nl.entreco.giddyapp.profile

import android.content.Context
import android.util.Log
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
        Log.i("PROFILE", "PROFILE init $this")
        fetchProfileUsecase.go { profile ->
            currentProfile.set(profile)
            Log.i("PROFILE", "PROFILE fetched current:${state.value} new: $profile")
            state.postValue(profile)
        }
    }

    fun version(context: Context): String =
        context.applicationContext.packageManager.getPackageInfo(context.packageName, 0).versionName

    override fun onCleared() {
        super.onCleared()
        fetchProfileUsecase.clear()
        Log.i("PROFILE", "PROFILE onCleared ProfileViewModel $this")
    }
}