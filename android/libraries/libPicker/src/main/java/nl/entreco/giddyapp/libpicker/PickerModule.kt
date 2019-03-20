package nl.entreco.giddyapp.libpicker

import android.app.Activity
import com.google.firebase.FirebaseApp
import com.google.firebase.ml.vision.FirebaseVision
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.libpicker.labeller.MlKitImageLabeller
import nl.entreco.giddyapp.libpicker.moderator.ApplyModerationUsecase
import nl.entreco.giddyapp.libpicker.picker.ImageCropper
import nl.entreco.giddyapp.libpicker.picker.ImageHelper
import nl.entreco.giddyapp.libpicker.picker.ThirdPartyImagePicker

@Module
object PickerModule {

    @Provides
    @JvmStatic
    internal fun provideImageLabeller(app: FirebaseApp): ImageLabeller {
        return MlKitImageLabeller(FirebaseVision.getInstance(app).onDeviceImageLabeler)
    }

    @Provides
    @JvmStatic
    fun provideImagePicker(activity: Activity, labeller: ImageLabeller): ImagePicker {
        val helper = ImageHelper(activity)
        val cropper = ImageCropper(activity)
        val moderation = ApplyModerationUsecase(labeller)
        return ThirdPartyImagePicker(activity, helper, cropper, moderation)
    }


}