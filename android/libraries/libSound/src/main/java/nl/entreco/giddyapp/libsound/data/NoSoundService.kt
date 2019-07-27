package nl.entreco.giddyapp.libsound.data

import nl.entreco.giddyapp.libsound.SoundService

internal class NoSoundService : SoundService {
    override fun play(sound: Int) {}
}