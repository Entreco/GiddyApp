package nl.entreco.giddyapp.viewer.init

import android.net.Uri
import nl.entreco.giddyapp.libhorses.Horse

sealed class InitViewerResponse {
    data class Initialized(val uri: Uri?, val horses: List<Horse>) : InitViewerResponse()
    data class Error(val msg: String) : InitViewerResponse()
    object NoUser : InitViewerResponse()
}