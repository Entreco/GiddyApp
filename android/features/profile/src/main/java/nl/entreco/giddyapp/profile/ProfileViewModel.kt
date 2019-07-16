package nl.entreco.giddyapp.profile

import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libcore.toSingleEvent
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.profile.matches.FetchMatchesUsecase
import nl.entreco.giddyapp.profile.profile.FetchProfileUsecase
import nl.entreco.giddyapp.profile.profile.Profile
import nl.entreco.giddyapp.profile.profile.ProfileItem
import javax.inject.Inject

class ProfileViewModel @Inject constructor(
    private val fetchProfileUsecase: FetchProfileUsecase,
    private val fetchMatchesUsecase: FetchMatchesUsecase
) : ViewModel(), ProfileItem.OnClick {

    val currentProfile = ObservableField<Profile>()
    val items = ObservableArrayList<ProfileItem>()
    val footerMessage = ObservableInt(R.string.profile_star_empty)

    private val state = MutableLiveData<Profile>()
    private val selected = MutableLiveData<ProfileItem>()
    private val matches = MutableLiveData<List<UserLike>>()

    fun state(): LiveData<Profile> = state
    fun clicks(): LiveData<ProfileItem> = selected.toSingleEvent()
    fun matches(): LiveData<List<UserLike>> = matches

    init {
        state.postValue(null)
        fetchProfileUsecase.go("profile") { profile ->
            currentProfile.set(profile)
            state.postValue(profile)
            footerMessage.set(profile.footer())
            generateItems(profile.account)
        }
        fetchMatchesUsecase.go { horses ->
            matches.postValue(horses)
        }
    }

    override fun onClick(item: ProfileItem) {
        if (selected.value != item) selected.postValue(item)
    }

    private fun generateItems(account: Account) {
        items.clear()
        val _items = when (account) {
            is Account.Authenticated -> ProfileItem.all(this)
            is Account.Anomymous -> ProfileItem.anonymous(this)
            else -> ProfileItem.error()

        }
        items.addAll(_items)
    }

    override fun onCleared() {
        super.onCleared()
        fetchProfileUsecase.clear("profile")
    }
}