package nl.entreco.giddyapp.libpicker

import android.net.Uri
import nl.entreco.giddyapp.libcore.HexString

data class SelectedImage(val id: String, val name: String, val uri: Uri, val labels: List<String> = emptyList(), val startColor: HexString = HexString.White, val endColor: HexString = HexString.White)