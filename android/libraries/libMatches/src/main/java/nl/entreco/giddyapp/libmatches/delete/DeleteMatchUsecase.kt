package nl.entreco.giddyapp.libmatches.delete

import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libcore.onUi
import nl.entreco.giddyapp.libmatches.MatchService
import javax.inject.Inject

class DeleteMatchUsecase @Inject constructor(
    private val matchService: MatchService
) {

    fun go(request: DeleteMatchRequest, done: (DeleteMatchResponse) -> Unit) {
        onBg {
            matchService.delete(request.uuid, request.horseId) { result ->
                val response = when (result) {
                    null -> DeleteMatchResponse.Success
                    else -> DeleteMatchResponse.Fail(result)
                }
                onUi { done(response) }
            }
        }
    }
}