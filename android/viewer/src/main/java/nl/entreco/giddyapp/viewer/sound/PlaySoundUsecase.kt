package nl.entreco.giddyapp.viewer.sound

import androidx.annotation.RawRes
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.viewer.SoundService
import javax.inject.Inject

class PlaySoundUsecase @Inject constructor(
    private val soundService: SoundService
) {
    fun play(@RawRes sound: Int) {
        onBg {
            soundService.play(sound)
        }
    }
}