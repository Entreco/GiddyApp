package nl.entreco.giddyapp.libcore.launch

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.di.AppScope
import nl.entreco.giddyapp.libcore.toSingleEvent
import javax.inject.Inject

class DynamicLauncher @Inject constructor(@AppScope private val manager: SplitInstallManager) {

    private val stateListener = SplitInstallStateUpdatedListener { state ->
        events.postValue(state.status())
        Log.i("YOGO", "install status: ${state.status()} ${state.errorCode()} ${state.moduleNames()}")
        when (state.status()) {
            SplitInstallSessionStatus.INSTALLED -> unregister()
        }
    }

    private val events = MutableLiveData<Int>()

    fun listen(module: String): LiveData<Int> {
        return events.toSingleEvent().also {
            when (manager.installedModules.contains(module)) {
                true -> launch()
                else -> requestInstall(module)
            }
        }
    }

    private fun launch() {
        events.postValue(SplitInstallSessionStatus.INSTALLED)
    }

    private fun requestInstall(module: String) {
        manager.registerListener(stateListener)
        val request = SplitInstallRequest.newBuilder().addModule(module).build()
        manager.startInstall(request)
    }

    private fun unregister() {
        manager.unregisterListener(stateListener)
    }
}