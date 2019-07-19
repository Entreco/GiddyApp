package nl.entreco.giddyapp.libhorses

import android.net.Uri

sealed class DownloadUrl {
    data class Ok(val uri: Uri) : DownloadUrl()
    data class Err(val msg: String) : DownloadUrl()
}