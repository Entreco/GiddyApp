package nl.entreco.giddyapp

import android.graphics.PixelFormat
import android.os.Bundle
import nl.entreco.giddyapp.libcore.LaunchHelper
import nl.entreco.giddyapp.libcore.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaunchHelper.launchViewer(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBX_8888)
    }
}
