package nl.entreco.giddyapp.libcore.launch.features

import android.app.Activity
import android.content.Context
import android.util.Log
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
            Log.w("INSTALL", "DYNMODULE update: $update")
            Log.w("INSTALL", "DYNMODULE update.status: ${update.status()}")
            Log.w("INSTALL", "DYNMODULE update.resIntent: ${update.resolutionIntent()}")
            Log.w("INSTALL", "DYNMODULE update.errCode: ${update.errorCode()}")
            toast(context, "Unknown error ${update.status()}")
//            if (mySessionId.get() == update.sessionId()) {
                Log.w(
                    "INSTALL",
                    "DYNMODULE session match: ${update.bytesDownloaded()} ${update.totalBytesToDownload()}"
                )
                installedAction(update.bytesDownloaded(), update.totalBytesToDownload(), false)
                when (update.status()) {
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> manager.startConfirmationDialogForResult(
                        update,
                        context,
                        requestCode
                    )
                    SplitInstallSessionStatus.INSTALLING -> installedAction(1, 1, false)
                    SplitInstallSessionStatus.INSTALLED -> {
                        SplitInstallHelper.updateAppInfo(context.application)
                        ready(installedAction)
                    }
                }
//            }
        }

        manager.startInstall(request)
//            .addOnFailureListener { err ->
//                Log.w("INSTALL", "DYNMODULE failure: $err")
//                when (val code = (err as? SplitInstallException)?.errorCode) {
//                    SplitInstallErrorCode.ACCESS_DENIED -> toast(context, "Access Denied: $featureName")
//                    SplitInstallErrorCode.NETWORK_ERROR -> toast(context, "Network error: $featureName retry?")
//                    SplitInstallErrorCode.API_NOT_AVAILABLE -> toast(
//                        context,
//                        "Api not available: $featureName update play services?"
//                    )
//                    SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> toast(
//                        context,
//                        "Incompatible with existing session: ${mySessionId.get()}"
//                    )
//                    SplitInstallErrorCode.MODULE_UNAVAILABLE -> toast(context, "Module Unavailable: $featureName")
//                    else -> toast(context, "Unknown error $code")
//                }
//            }
//            .addOnSuccessListener { session ->
//                Log.w("INSTALL", "DYNMODULE success: $session")
//                mySessionId.set(session)
//                toast(context, "Starting install $session")
//            }.addOnCompleteListener { task ->
//                toast(context, "DYNMODULE complete: task")
//            }
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