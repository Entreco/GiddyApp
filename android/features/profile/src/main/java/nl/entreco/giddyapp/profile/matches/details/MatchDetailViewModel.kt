package nl.entreco.giddyapp.profile.matches.details

import android.net.Uri
import androidx.databinding.ObservableField
import androidx.databinding.ObservableInt
import androidx.lifecycle.ViewModel
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.profile.R
import javax.inject.Inject

class MatchDetailViewModel @Inject constructor(
    private val fetchDetailsUsecase: FetchDetailsUsecase
) : ViewModel() {

    val image = ObservableField<Uri?>(Horse.Loading.imageUri)
    val reference = ObservableField(Horse.Loading.imageRef)
    val horseName = ObservableField(Horse.Loading.name)
    val horseDescription = ObservableField("")
    val matchDetail1 = ObservableField("")
    val matchDetail2 = ObservableField("")
    val matchDetail3 = ObservableField("")
    val matchDetail4 = ObservableField("")
    val matchDetail5 = ObservableField("")

    fun fetch(horseId: String?) {
        fetchDetailsUsecase.go(FetchDetailsRequest(horseId)) { response ->
            when(response){
                is FetchDetailsResponse.Succes -> {
                    image.set(response.horse.imageUri)
                    reference.set(response.horse.imageRef)
                    horseName.set(response.horse.details.name)
                    horseDescription.set(response.horse.details.desc)
                    matchDetail1.set(response.horse.details.gender.name)
                    matchDetail2.set(response.horse.details.category.name)
                    matchDetail3.set(response.horse.details.price.name)
                    matchDetail4.set(response.horse.details.ratio.ratio)
                    matchDetail5.set(response.horse.details.type.name)
                }
                is FetchDetailsResponse.Error -> {}
            }
        }
    }
}