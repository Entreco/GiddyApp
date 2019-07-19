package nl.entreco.giddyapp

import android.app.Application
import nl.entreco.giddyapp.libcore.di.DiProvider

class TestGiddyApp : Application(), DiProvider<FeatureComponent> {
    override fun get(): FeatureComponent {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}