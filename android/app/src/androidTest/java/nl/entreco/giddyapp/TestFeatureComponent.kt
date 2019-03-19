package nl.entreco.giddyapp

import android.content.Context
import android.util.DisplayMetrics
import androidx.test.platform.app.InstrumentationRegistry
import com.google.firebase.FirebaseApp
import nl.entreco.giddyapp.libcore.launch.DynamicLauncher
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libimg.loader.ImageLoader

class TestFeatureComponent : FeatureComponent {
    override fun appContext(): Context = InstrumentationRegistry.getInstrumentation().context

    override fun horseService(): HorseService = TestHorseService()

    override fun imageLoader(): ImageLoader {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun metrics(): DisplayMetrics {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun fbApp(): FirebaseApp {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override fun dynamicLauncher(): DynamicLauncher {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}