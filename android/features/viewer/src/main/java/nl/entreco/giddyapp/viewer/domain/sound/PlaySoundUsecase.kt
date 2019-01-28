package nl.entreco.giddyapp.viewer.domain.sound

import androidx.annotation.RawRes
import nl.entreco.giddyapp.libs.horses.onBg
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