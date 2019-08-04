package nl.entreco.giddyapp.profile.matches.details

import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.fetch.FilterOptions
import javax.inject.Inject

class FetchDetailsUsecase @Inject constructor(
    private val horseService: HorseService
) {

    fun go(request: FetchDetailsRequest, done: (FetchDetailsResponse) -> Unit) {
        onBg {
            if(request.horseId.isNullOrBlank()) onUi { done(FetchDetailsResponse.Error(Horse.None)) }
            else {
                horseService.retrieve(listOf(request.horseId), FilterOptions()) { horses ->
                    val response = when {
                        horses.size == 1 && horses[0] is Horse.Normal -> FetchDetailsResponse.Succes(horses[0] as Horse.Normal)
                        horses.isEmpty() -> FetchDetailsResponse.NotFound(Horse.NotFound(request.horseId))
                        else -> FetchDetailsResponse.Error(Horse.None)
                    }

                    onUi {
                        done(response)
                    }
                }
            }
        }
    }
}