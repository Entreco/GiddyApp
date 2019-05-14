package nl.entreco.giddyapp.profile

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.user.User
import nl.entreco.giddyapp.libcore.toSingleEvent
import nl.entreco.giddyapp.profile.profile.FetchProfileUsecase
import nl.entreco.giddyapp.profile.profile.Profile
import nl.entreco.giddyapp.profile.profile.ProfileItem
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    fetchProfileUsecase: FetchProfileUsecase
) : ViewModel(), ProfileItem.OnClick {

    val currentProfile = ObservableField<Profile>()
    val items = generateItems(this)

    private val state = MutableLiveData<Profile>()
    private val selected = MutableLiveData<ProfileItem>()

    fun state(): LiveData<Profile> = state.toSingleEvent()
    fun clicks(): LiveData<ProfileItem> = selected.toSingleEvent()

    init {
        state.postValue(null)
        fetchProfileUsecase.go { profile ->
            currentProfile.set(profile)
            when (profile.user) {
                is User.Anomymous -> state.postValue(profile)
                is User.Authenticated -> state.postValue(profile)
                is User.Error -> state.postValue(profile)
            }
        }
    }

    override fun onClick(item: ProfileItem) {
        if (selected.value != item) selected.postValue(item)
    }

    private fun generateItems(onClick: ProfileItem.OnClick) = ObservableArrayList<ProfileItem>().apply {
        addAll(ProfileItem.all(onClick))
    }
}