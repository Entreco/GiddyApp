package nl.entreco.giddyapp

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import nl.entreco.giddyapp.core.LaunchHelper

class SplashActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        LaunchHelper.launchViewer(this, null)
    }
}
