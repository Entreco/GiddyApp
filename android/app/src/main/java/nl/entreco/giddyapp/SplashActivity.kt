package nl.entreco.giddyapp

import android.graphics.PixelFormat
import android.os.Bundle
import nl.entreco.giddyapp.core.LaunchHelper
import nl.entreco.giddyapp.core.base.BaseActivity

class SplashActivity : BaseActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        MainActivity.launch(this)
    }

    override fun onAttachedToWindow() {
        super.onAttachedToWindow()
        window.setFormat(PixelFormat.RGBX_8888)
    }
}
