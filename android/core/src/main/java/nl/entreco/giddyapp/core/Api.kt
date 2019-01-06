package nl.entreco.giddyapp.core

import android.content.Context
import kotlin.random.Random

class Api constructor(context: Context) {
    fun fetch(): Int {
        return Random.nextInt(5, 50)
    }
}