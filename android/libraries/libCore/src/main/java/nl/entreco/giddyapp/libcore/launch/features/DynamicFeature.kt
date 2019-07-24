package nl.entreco.giddyapp.libcore.launch.features

import android.app.Activity
import android.content.Context
import android.os.Build
import android.widget.Toast
import com.google.android.play.core.splitinstall.*
import com.google.android.play.core.splitinstall.model.SplitInstallErrorCode
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import java.util.concurrent.atomic.AtomicInteger

const val CREATOR_INSTALL_CODE = 1111
const val PROFILE_INSTALL_CODE = 1112
const val VIEWER_INSTALL_CODE = 1113

abstract class DynamicFeature<T>(
    private val activityName: String,
    private val featureName: String,
    private val requestCode: Int
) {

    private val mySessionId = AtomicInteger(0)

    fun launch(context: Activity, installedAction: (Long, Long, Boolean) -> Unit) {
        val manager = SplitInstallManagerFactory.create(context.application)

        if (manager.installedModules.contains(featureName)) {
            SplitInstallHelper.updateAppInfo(context.application)
            ready(installedAction)
        } else {
            download(context, manager, installedAction)
        }
    }

    private fun download(
        context: Activity,
        manager: SplitInstallManager,
        installedAction: (Long, Long, Boolean) -> Unit
    ) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(featureName)
            .build()

        manager.registerListener { update ->
            toast(context, "Status: ${update.status()}")
            if (mySessionId.get() == update.sessionId()) {
                installedAction(update.bytesDownloaded(), update.totalBytesToDownload(), false)
                when (update.status()) {
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> manager.startConfirmationDialogForResult(
                        update,
                        context,
                        requestCode
                    )
                    SplitInstallSessionStatus.INSTALLING -> installedAction(1, 1, false)
                    SplitInstallSessionStatus.INSTALLED -> {
                        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                            SplitInstallHelper.updateAppInfo(context.application)
                        }
                        ready(installedAction)
                    }
                }
            }
        }

        manager.startInstall(request)
            .addOnFailureListener { err ->
                when (val code = (err as? SplitInstallException)?.errorCode) {
                    SplitInstallErrorCode.ACCESS_DENIED -> toast(context, "Access Denied: $featureName")
                    SplitInstallErrorCode.NETWORK_ERROR -> toast(context, "Network error: $featureName retry?")
                    SplitInstallErrorCode.API_NOT_AVAILABLE -> toast(
                        context,
                        "Api not available: $featureName update play services?"
                    )
                    SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> toast(
                        context,
                        "Incompatible session: ${mySessionId.get()}"
                    )
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

    private fun ready(installedAction: (Long, Long, Boolean) -> Unit) {
        installedAction(1, 1, true)
    }

}