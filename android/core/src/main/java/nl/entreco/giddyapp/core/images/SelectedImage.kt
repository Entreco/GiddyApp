package nl.entreco.giddyapp.core.images

import android.net.Uri

data class SelectedImage(val id: String, val name: String, val uri: Uri, val startColor: String = "#ffffff", val endColor: String = "#ffffff")