package nl.entreco.giddyapp.viewer.init

import android.content.Context
import android.net.Uri
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libcore.onBg
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseUsecase
import javax.inject.Inject

class InitViewerUseCase @Inject constructor(
    @AppContext private val context: Context,
    private val authenticator: Authenticator,
    private val fetchHorseUsecase: FetchHorseUsecase
) {

    fun go(request: InitViewerRequest, done: (InitViewerResponse) -> Unit) {
        onBg {

            authenticator.signinOrAnonymous(context) {
                authenticator.observe("init") { account ->
                    when (account) {
                        is Account.Authenticated -> fetchHorses(request.horseId, account.url, done)
                        is Account.Anomymous -> fetchHorses(request.horseId, null, done)
                        is Account.Error -> reportError(account.msg, done)
                    }
                }
            }
        }
    }

    private fun fetchHorses(horseId: String?, icon: Uri?, done: (InitViewerResponse) -> Unit) {
        fetchHorseUsecase.go(FetchHorseRequest(horseId)) { response ->
            done(InitViewerResponse.Initialized(icon, response.horses))
        }
    }

    private fun reportError(msg: String, done: (InitViewerResponse) -> Unit) {
        done(InitViewerResponse.Error(msg))
    }

    fun clear() {
        onBg {
            authenticator.stopObserving("init")
        }
    }
}