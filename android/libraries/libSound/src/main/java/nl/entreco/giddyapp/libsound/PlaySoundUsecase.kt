package nl.entreco.giddyapp.libsound

import nl.entreco.giddyapp.libcore.onBg
import javax.inject.Inject

class PlaySoundUsecase @Inject constructor(
    private val soundService: SoundService
) {
    fun like() {
        onBg {
            soundService.play(R.raw.like)
        }
    }

    fun dislike() {
        onBg {
            soundService.play(R.raw.dislike)
        }
    }
}