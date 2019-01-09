package nl.entreco.giddyapp

import android.graphics.PixelFormat
import android.os.Bundle
import nl.entreco.giddyapp.core.base.BaseActivity
import nl.entreco.giddyapp.core.LaunchHelper

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaunchHelper.launchViewer(this, null)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBX_8888)
    }
}
