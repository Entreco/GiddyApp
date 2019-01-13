package nl.entreco.giddyapp

import android.graphics.PixelFormat
import android.os.Bundle
import android.util.Log
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.SplitInstallStateUpdatedListener
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus.*
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.base.BaseActivity

class SplashActivity : BaseActivity() {

    private val manager by lazy { SplitInstallManagerFactory.create(this) }

    private val stateListener by lazy {
        SplitInstallStateUpdatedListener { state ->
            Log.i("DOH", "update: $state")
            when (state.status()) {
                PENDING -> {
                }
                DOWNLOADING -> {
                }
                DOWNLOADED -> {
                }
                INSTALLED -> {
                }
                INSTALLING -> {
                }
                REQUIRES_USER_CONFIRMATION -> {
                }
                FAILED -> {
                }
                CANCELING -> {
                }
                CANCELED -> {
                }
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        LaunchHelper.launchViewer(this, null)

//        if (manager.installedModules.contains(getString(R.string.title_viewer))) {
//            LaunchHelper.launchViewer(this, null)
//        } else {
//
//            val request = SplitInstallRequest.newBuilder()
//                .addModule(getString(R.string.title_viewer))
//                .build()
//
//            manager.registerListener(stateListener)
//            manager.startInstall(request)
//                .addOnSuccessListener { sessionId ->
//                    when (sessionId) {
//                        SplitInstallSessionStatus.INSTALLED -> LaunchHelper.launchViewer(this, null)
//                        SplitInstallSessionStatus.DOWNLOADING -> Log.i("DOH", "downloading $sessionId")
//                        else -> Log.i("DOH", "successListener: $sessionId")
//                    }
//                }.addOnFailureListener { exception ->
//                    Log.i("DOH", "error: $exception")
//                }.addOnCompleteListener {
//                    Log.i("DOH", "complete: ${it.isSuccessful}")
//                }
//        }

    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBX_8888)
    }
}
