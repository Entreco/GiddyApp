package nl.entreco.giddyapp.profile.menu

import androidx.databinding.ObservableArrayList
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libcore.toSingleEvent
import javax.inject.Inject

class MenuViewModel @Inject constructor(

) : ViewModel(), MenuItem.OnClick {

    val items = ObservableArrayList<MenuItem>()
    private val selected = MutableLiveData<MenuItem>()
    fun clicks(): LiveData<MenuItem> = selected.toSingleEvent()

    fun generateItems(account: Account) {
        items.clear()
        val profileItems = when (account) {
            is Account.Authenticated -> MenuItem.all(this)
            is Account.Anomymous -> MenuItem.anonymous(this)
            else -> MenuItem.error()

        }
        items.addAll(profileItems)
    }

    override fun onClick(item: MenuItem) {
        if (selected.value != item) selected.postValue(item)
    }

    fun clearClicks() {
        selected.postValue(null)
    }

    override fun onCleared() {
        super.onCleared()
        clearClicks()
    }
}