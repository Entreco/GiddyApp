package nl.entreco.giddyapp.viewer

import androidx.annotation.RawRes

interface SoundService {
    fun play(@RawRes sound: Int)
}