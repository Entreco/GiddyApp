package nl.entreco.giddyapp.libcore.launch.features

import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleObserver
import androidx.lifecycle.OnLifecycleEvent
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus

const val CREATOR_INSTALL_CODE = 1111
const val PROFILE_INSTALL_CODE = 1112
const val VIEWER_INSTALL_CODE = 1113

class DynamicFeature(
    private val manager: SplitInstallManager,
    private val featureName: String,
    private val update: (Status) -> Unit
) : LifecycleObserver, SplitInstallStateUpdatedListener {

    override fun onStateUpdate(update: SplitInstallSessionState) {
        update(Status.Update(update.bytesDownloaded(), update.totalBytesToDownload()))
        when (update.status()) {
            SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> update(Status.Confirm(update))
            SplitInstallSessionStatus.INSTALLING -> {
            }
            SplitInstallSessionStatus.INSTALLED -> {
                manager.unregisterListener(this)
                update(Status.Launch)
            }
        }
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_RESUME)
    fun onResume() {
        manager.registerListener(this)
    }

    @OnLifecycleEvent(Lifecycle.Event.ON_PAUSE)
    fun onPause() {
        manager.unregisterListener(this)
    }

    fun go() {
        if (manager.installedModules.contains(featureName)) {
            update(Status.Launch)
        } else {
            val request = SplitInstallRequest.newBuilder()
                .addModule(featureName)
                .build()

            manager.startInstall(request)
                .addOnFailureListener { err ->
                    when (val code = (err as? SplitInstallException)?.errorCode) {
                        SplitInstallErrorCode.ACCESS_DENIED -> update(Status.Error("Access Denied"))
                        SplitInstallErrorCode.NETWORK_ERROR -> update(Status.Error("Network error: retry?"))
                        SplitInstallErrorCode.API_NOT_AVAILABLE -> update(Status.Error("Api not available: update play services?"))
                        SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> update(Status.Error("Incompatible session"))
                        SplitInstallErrorCode.MODULE_UNAVAILABLE -> update(Status.Error("Module Unavailable"))
                        else -> update(Status.Error("Unknown error $code"))
                    }
                }
        }
    }
}