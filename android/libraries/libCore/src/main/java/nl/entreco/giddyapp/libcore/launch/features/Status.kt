package nl.entreco.giddyapp.libcore.launch.features

import com.google.android.play.core.splitinstall.SplitInstallSessionState

sealed class Status {
    object Launch : Status()
    data class Confirm(val state: SplitInstallSessionState): Status()
    data class Update(val current: Long, val total: Long): Status()
    data class Error(val msg: String) : Status()
}