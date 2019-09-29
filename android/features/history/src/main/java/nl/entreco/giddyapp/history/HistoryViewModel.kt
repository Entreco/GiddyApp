package nl.entreco.giddyapp.history

import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.history.details.FetchDetailsRequest
import nl.entreco.giddyapp.history.details.FetchDetailsResponse
import nl.entreco.giddyapp.history.details.FetchDetailsUsecase
import nl.entreco.giddyapp.libauth.user.UserLike
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorsePosted
import nl.entreco.giddyapp.libhorses.HorseRatio
import nl.entreco.giddyapp.libmatches.delete.DeleteMatchRequest
import nl.entreco.giddyapp.libmatches.delete.DeleteMatchResponse
import nl.entreco.giddyapp.libmatches.delete.DeleteMatchUsecase
import javax.inject.Inject

class HistoryViewModel @Inject constructor(
    private val fetchMatchesUsecase: FetchMatchesUsecase,
    private val fetchDetailsUsecase: FetchDetailsUsecase,
    private val deleteMatchUsecase: DeleteMatchUsecase
) : ViewModel() {

    val isEmpty = ObservableBoolean(true)
    val ratio = ObservableField<HorseRatio>()
    val since = ObservableField<HorsePosted>()
    private val _horseDetails = mutableMapOf<String, Horse>()

    private val selectedItem = MutableLiveData<Pair<UserLike?, Type>>()
    fun selected(): LiveData<Pair<UserLike?, Type>> = selectedItem

    private val matches = MutableLiveData<List<UserLike>>()
    fun matches(): LiveData<List<UserLike>> = matches

    private val horseDetails = MutableLiveData<Map<String, Horse>>()
    fun details(): LiveData<Map<String, Horse>> = horseDetails

    fun retrieveMatches(uid: String) {
        fetchMatchesUsecase.go(FetchMatchesRequest(uid)) { likes ->
            matches.postValue(likes)
            isEmpty.set(likes.isEmpty())
        }
    }

    fun deleteMatch(uid: String, onEmpty: () -> Unit) {
        selectedItem.value?.first?.let { like ->
            like.horseId.let { horseId ->
                deleteMatchUsecase.go(DeleteMatchRequest(uid, horseId)) { response ->
                    when (response) {
                        is DeleteMatchResponse.Success -> {
                            val m = matches.value?.filter { it != like }
                            matches.postValue(m)
                            isEmpty.set(m?.isEmpty() ?: true)
                            if (isEmpty.get()) {
                                onEmpty()
                            }
                        }
                        is DeleteMatchResponse.Fail -> { /* Handle exception */ }
                    }
                }
            }
        }
    }

    fun selectedItem(position: Int, type: Type) {
        val selected = matches.value?.getOrElse(position) { UserLike("none", "none", "none") }
        selectedItem.postValue(Pair(selected, type))
    }

    fun loadDetails(userLike: UserLike) {
        fetchDetailsUsecase.go(FetchDetailsRequest(userLike.horseId)) { response ->
            when (response) {
                is FetchDetailsResponse.Succes -> {
                    _horseDetails.putIfAbsent(response.horse._id, response.horse)
                    horseDetails.postValue(_horseDetails)
                    ratio.set(response.horse.details.ratio)
                    since.set(response.horse.details.since)
                }
            }
        }
    }
}

sealed class Type {
    object Paging : Type()
    object Selecting : Type()
    object Initial : Type()
}