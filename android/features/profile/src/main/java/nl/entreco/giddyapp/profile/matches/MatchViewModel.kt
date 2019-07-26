package nl.entreco.giddyapp.profile.matches

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.profile.profile.Profile
import javax.inject.Inject

class MatchViewModel @Inject constructor(
    private val fetchMatchesUsecase: FetchMatchesUsecase
) : ViewModel() {

    private val matches = MutableLiveData<List<UserLike>>()
    fun matches(): LiveData<List<UserLike>> = matches

    fun retrieveMatches(account: Profile) {
        fetchMatchesUsecase.go(FetchMatchesRequest(account.uid())) { horses ->
            matches.postValue(horses)
        }
    }
}