package nl.entreco.giddyapp.viewer

import android.content.Context
import android.media.SoundPool
import nl.entreco.giddyapp.core.onBg
import nl.entreco.giddyapp.core.onUi
import java.util.concurrent.atomic.AtomicBoolean

class SoundPoolService(
    private val context: Context,
    private val soundPool: SoundPool
) : SoundService {

    private val prio = 1
    private val map = mutableMapOf<Int, Int>()
    private val prepared = AtomicBoolean(false)

    init {

        onBg {
            map[R.raw.like] = soundPool.load(context, R.raw.like, prio)
            map[R.raw.dislike] = soundPool.load(context, R.raw.dislike, prio)
            onUi { prepared.set(true) }
        }
    }

    override fun play(sound: Int) {
        if(prepared.get()) {
            soundPool.play(map[sound] ?: 0, 1F, 1F, prio, 0, 1F)
        }
    }
}