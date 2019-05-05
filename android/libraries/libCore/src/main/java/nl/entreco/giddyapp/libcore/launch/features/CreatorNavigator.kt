package nl.entreco.giddyapp.libcore.launch.features

import android.content.Context
import android.content.Intent
import com.google.android.play.core.splitinstall.SplitInstallManager
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import com.google.android.play.core.splitinstall.SplitInstallRequest
import com.google.android.play.core.splitinstall.model.SplitInstallSessionStatus
import nl.entreco.giddyapp.libcore.launch.loadIntentOrNull

object CreatorNavigator : DynamicFeature<Intent> {
    private const val HOME = "nl.entreco.giddyapp.creator.CreatorActivity"
    private const val FEATURE = "creator"

    override val dynamicStart: Intent?
        get() = HOME.loadIntentOrNull()

    fun launch(c: Context, installedAction: (Long, Intent?) -> Unit) {
        val manager = SplitInstallManagerFactory.create(c)
        if(manager.installedModules.contains(FEATURE)) ready(installedAction)
        else {
            download(manager, installedAction)
        }
    }

    private fun download(
        manager: SplitInstallManager,
        installedAction: (Long, Intent?) -> Unit
    ) {
        val request = SplitInstallRequest.newBuilder()
            .addModule(FEATURE)
            .build()
        manager.registerListener {
            installedAction(it.bytesDownloaded() / it.totalBytesToDownload(), null)
            if (it.status() == SplitInstallSessionStatus.INSTALLED) ready(installedAction)
        }
        manager.startInstall(request)
    }

    private fun ready(installedAction: (Long, Intent?) -> Unit) {
        installedAction(1, dynamicStart)
    }
}