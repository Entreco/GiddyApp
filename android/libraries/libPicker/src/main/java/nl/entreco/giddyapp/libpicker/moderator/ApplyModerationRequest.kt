package nl.entreco.giddyapp.libpicker.moderator

import android.content.Context
import android.net.Uri

data class ApplyModerationRequest(
    val context: Context,
    val uri: Uri,
    val required: List<String>,
    val blocked: List<String>
)