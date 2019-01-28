package nl.entreco.giddyapp.viewer.domain.sound

import androidx.annotation.RawRes

interface SoundService {
    fun play(@RawRes sound: Int)
}