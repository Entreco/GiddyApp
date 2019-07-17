package nl.entreco.giddyapp

import android.graphics.PixelFormat
import android.os.Bundle
import nl.entreco.giddyapp.libcore.base.BaseActivity
import nl.entreco.giddyapp.libcore.launch.LaunchHelper
import nl.entreco.giddyapp.libcore.launch.features.ViewerNavigator

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ViewerNavigator.launch(this) { progress, intent ->
            intent?.let {
                LaunchHelper.launchViewer(this)
                finish()
            }
        }
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBX_8888)
    }
}
