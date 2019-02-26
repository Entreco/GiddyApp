package nl.entreco.giddyapp.libpicker

import android.app.Activity
import android.util.Log
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.core.di.AppScope

@Module
object PickerModule {

    @Provides
    @JvmStatic
    fun provideImagePicker(activity: Activity): ImagePicker {
        Log.i("DEPINJ", "provide imagePicker")
        return ThirdPartyImagePicker(activity)
    }
}