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

abstract class DynamicFeature<T>(private val activityName: String, private val featureName: String) {
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
        manager.registerListener {
            installedAction(it.bytesDownloaded() / it.totalBytesToDownload(), null)
            if (it.status() == SplitInstallSessionStatus.INSTALLED) ready(installedAction)
        }
        manager.startInstall(request)
            .addOnFailureListener { err ->
                when (val code = (err as? SplitInstallException)?.errorCode) {
                    SplitInstallErrorCode.MODULE_UNAVAILABLE -> toast(context, "Module Unavailable: $featureName")
                    else -> toast(context, "Unknown error $code")
                }
            }
            .addOnSuccessListener { session ->
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