package nl.entreco.giddyapp.libpicker

import android.net.Uri
import nl.entreco.giddyapp.core.HexString

data class SelectedImage(val id: String, val name: String, val uri: Uri, val startColor: HexString = HexString.White, val endColor: HexString = HexString.White)