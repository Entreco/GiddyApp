package nl.entreco.giddyapp.libsound

import androidx.annotation.RawRes

interface SoundService {
    fun play(@RawRes sound: Int)
}