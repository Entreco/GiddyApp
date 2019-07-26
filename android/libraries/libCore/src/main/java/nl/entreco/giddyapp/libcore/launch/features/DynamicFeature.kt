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

    fun launch(context: Activity, installedAction: (Long, Long, Boolean) -> Unit) {
        val manager = SplitInstallManagerFactory.create(context.application)

        if (manager.installedModules.contains(featureName)) {
            SplitInstallHelper.updateAppInfo(context.application)
            installedAction(1, 1, true)
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

        UpdateListener(manager, installedAction,
            updateAction = {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
                    SplitInstallHelper.updateAppInfo(context.application)
                }
            }, confirmAction = { update ->
                manager.startConfirmationDialogForResult(
                    update,
                    context,
                    requestCode
                )
            }
        ).start(request) { msg ->
            toast(context, msg)
        }
    }

    private fun toast(context: Context, msg: String) {
        Toast.makeText(
            context,
            msg,
            Toast.LENGTH_SHORT
        ).show()
    }

    class UpdateListener(
        private val manager: SplitInstallManager,
        private val installedAction: (Long, Long, Boolean) -> Unit,
        private val updateAction: () -> Unit,
        private val confirmAction: (SplitInstallSessionState) -> Unit
    ) : SplitInstallStateUpdatedListener {

        private val session = AtomicInteger(0)

        init {
            manager.registerListener(this)
        }

        override fun onStateUpdate(update: SplitInstallSessionState) {
            if (session.get() == update.sessionId()) {
                installedAction(update.bytesDownloaded(), update.totalBytesToDownload(), false)
                when (update.status()) {
                    SplitInstallSessionStatus.REQUIRES_USER_CONFIRMATION -> confirmAction(update)
                    SplitInstallSessionStatus.INSTALLING -> installedAction(1, 1, false)
                    SplitInstallSessionStatus.INSTALLED -> {
                        updateAction()
                        installedAction(1, 1, true)
                        manager.unregisterListener(this)
                    }
                }
            }
        }

        fun start(request: SplitInstallRequest?, toast: (String) -> Unit) {
            manager.startInstall(request)
                .addOnFailureListener { err ->
                    when (val code = (err as? SplitInstallException)?.errorCode) {
                        SplitInstallErrorCode.ACCESS_DENIED -> toast("Access Denied")
                        SplitInstallErrorCode.NETWORK_ERROR -> toast("Network error: retry?")
                        SplitInstallErrorCode.API_NOT_AVAILABLE -> toast("Api not available: update play services?")
                        SplitInstallErrorCode.INCOMPATIBLE_WITH_EXISTING_SESSION -> toast("Incompatible session: ${session.get()}")
                        SplitInstallErrorCode.MODULE_UNAVAILABLE -> toast("Module Unavailable")
                        else -> toast("Unknown error $code")
                    }
                }
                .addOnSuccessListener { sessionId ->
                    session.set(sessionId)
                    toast("Starting install $session")
                }
        }
    }
}