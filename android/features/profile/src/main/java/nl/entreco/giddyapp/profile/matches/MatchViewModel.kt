package nl.entreco.giddyapp.profile.matches

import android.view.View
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableInt
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class MatchViewModel @Inject constructor(
    private val fetchMatchesUsecase: FetchMatchesUsecase
) : ViewModel() {

    val isEmpty = ObservableBoolean(true)

    private val selectedItem = MutableLiveData<UserLike>()
    fun selected() : LiveData<UserLike> = selectedItem

    private val matches = MutableLiveData<List<UserLike>>()
    fun matches(): LiveData<List<UserLike>> = matches

    fun retrieveMatches(uid: String) {
        fetchMatchesUsecase.go(FetchMatchesRequest(uid)) { horses ->
            matches.postValue(horses)
            isEmpty.set(horses.isEmpty())
        }
    }

    fun selectedItem(position: Int) {
        val selected = matches.value?.getOrElse(position){ UserLike("none", "none", "none") }
        selectedItem.postValue(selected)
    }
}