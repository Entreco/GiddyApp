package nl.entreco.giddyapp.libhorses.fetch

import android.net.Uri

sealed class FetchImageResponse {
    data class Ok(val imageRef: String, val image: Uri) : FetchImageResponse()
    data class Err(val msg: String) : FetchImageResponse()
}