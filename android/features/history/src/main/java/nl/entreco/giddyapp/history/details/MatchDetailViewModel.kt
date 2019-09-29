package nl.entreco.giddyapp.history.details

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.history.models.BaseModel
import nl.entreco.giddyapp.libhorses.HorseDetail
import javax.inject.Inject

class MatchDetailViewModel @Inject constructor() : ViewModel() {

    private val model = MatchDetailModel()
    private val details = MutableLiveData<List<BaseModel>>()
    fun details(): LiveData<List<BaseModel>> = details

    fun show(horseDetails: HorseDetail) {
        model.fill(horseDetails)
        details.postValue(model.items())
    }

    fun showLoading() {
        details.postValue(model.items())
    }
}