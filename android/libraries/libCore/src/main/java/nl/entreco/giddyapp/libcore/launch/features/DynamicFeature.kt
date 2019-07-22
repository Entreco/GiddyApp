package nl.entreco.giddyapp.libcore.launch.features

import android.content.Context
import android.content.Intent
import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallException
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.launch.loadIntentOrNull
import java.util.concurrent.atomic.AtomicInteger

abstract class DynamicFeature<T>(private val activityName: String, private val featureName: String) {

    private val mySessionId = AtomicInteger(0)
    private val dynamicStart: Intent?
        get() = activityName.loadIntentOrNull()

    fun launch(context: Context, installedAction: (Long, Intent?) -> Unit) {
        val manager = SplitInstallManagerFactory.create(context)
        if (manager.installedModules.contains(featureName)) ready(installedAction)
        else {
            download(context, manager, installedAction)
        }
    }

    private fun download(
        context: Context,
        manager: SplitInstallManager,
        installedAction: (Long, Intent?) -> Unit
    ) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(featureName)
            .build()
        manager.registerListener { update ->
            if (mySessionId.get() == update.sessionId()) {
                installedAction(update.bytesDownloaded() / update.totalBytesToDownload(), null)
                if (update.status() == SplitInstallSessionStatus.INSTALLED) ready(installedAction)
            }
        }
        manager.startInstall(request)
            .addOnFailureListener { err ->
                when (val code = (err as? SplitInstallException)?.errorCode) {
                    SplitInstallErrorCode.ACCESS_DENIED -> toast(context, "Access Denied: $featureName")
                    SplitInstallErrorCode.NETWORK_ERROR -> toast(context, "Network error: $featureName retry?")
                    SplitInstallErrorCode.API_NOT_AVAILABLE -> toast(context, "Api not available: $featureName update play services?")
                    SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> toast(context, "Incompatible with existing session: ${mySessionId.get()}")
                    SplitInstallErrorCode.MODULE_UNAVAILABLE -> toast(context, "Module Unavailable: $featureName")
                    else -> toast(context, "Unknown error $code")
                }
            }
            .addOnSuccessListener { session ->
                mySessionId.set(session)
                toast(context, "Starting install $session")
            }
    }

    private fun toast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    private fun ready(installedAction: (Long, Intent?) -> Unit) {
        installedAction(1, dynamicStart)
    }

}