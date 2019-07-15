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
    private val fetchProfileUsecase: FetchProfileUsecase
) : ViewModel(), ProfileItem.OnClick {

    val currentProfile = ObservableField<Profile>()
    val items = ObservableArrayList<ProfileItem>()

    private val state = MutableLiveData<Profile>()
    private val selected = MutableLiveData<ProfileItem>()

    fun state(): LiveData<Profile> = state
    fun clicks(): LiveData<ProfileItem> = selected.toSingleEvent()
    
    init {
        state.postValue(null)
        fetchProfileUsecase.go("profile") { profile ->
            currentProfile.set(profile)
            state.postValue(profile)
            generateItems(profile.user)
        }
    }

    override fun onClick(item: ProfileItem) {
        if (selected.value != item) selected.postValue(item)
    }

    private fun generateItems(user: User) {
        items.clear()
        val _items = when(user){
            is User.Authenticated -> ProfileItem.all(this)
            is User.Anomymous -> ProfileItem.anonymous(this)
            else -> ProfileItem.error()

        }
        items.addAll(_items)
    }

    override fun onCleared() {
        super.onCleared()
        fetchProfileUsecase.clear("profile")
    }
}