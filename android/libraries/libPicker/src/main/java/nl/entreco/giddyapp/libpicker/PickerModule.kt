package nl.entreco.giddyapp.libpicker

import android.app.Activity
import dagger.Module
import dagger.Provides
import javax.inject.Inject

@Module
class PickerModule @Inject constructor(private val activity: Activity) {

    @Provides
    fun provideImagePicker(): ImagePicker {
        return ThirdPartyImagePicker(activity)
    }
}