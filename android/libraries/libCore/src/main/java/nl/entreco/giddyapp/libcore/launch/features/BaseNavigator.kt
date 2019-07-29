package nl.entreco.giddyapp.libcore.launch.features

import android.widget.Toast
import com.google.android.play.core.splitinstall.SplitInstallHelper
import com.google.android.play.core.splitinstall.SplitInstallManagerFactory
import nl.entreco.giddyapp.libcore.base.BaseActivity

abstract class BaseNavigator(
    private val featureName: String,
    private val requestCode: Int
) {
    fun launch(activity: BaseActivity, installed: (Long, Long, Boolean) -> Unit) {
        val manager = SplitInstallManagerFactory.create(activity)
        val lifecycle = activity.lifecycle
        val feature = DynamicFeature(manager, featureName) { status ->
            when (status) {
                is Status.Error -> {
                    Toast.makeText(activity, status.msg, Toast.LENGTH_SHORT).show()
                }
                is Status.Confirm -> manager.startConfirmationDialogForResult(
                    status.state, activity,
                    requestCode
                )
                is Status.Update -> installed(status.current, status.total, false)
                is Status.Launch -> {
                    SplitInstallHelper.updateAppInfo(activity.application)
                    installed(1, 1, true)
                }
            }
        }

        lifecycle.addObserver(feature)
        feature.go()
    }
}