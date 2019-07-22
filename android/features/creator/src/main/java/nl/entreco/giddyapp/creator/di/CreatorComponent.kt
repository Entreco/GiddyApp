package nl.entreco.giddyapp.creator.di

import android.app.Activity
import android.content.Context
import androidx.fragment.app.FragmentManager
import com.google.firebase.FirebaseApp
import dagger.BindsInstance
import dagger.Component
import nl.entreco.giddyapp.creator.CreatorAnimator
import nl.entreco.giddyapp.creator.CreatorViewModel
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.UserService
import nl.entreco.giddyapp.libcore.di.AppContext
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libimg.loader.ImageLoader
import nl.entreco.giddyapp.libpicker.ImagePicker
import nl.entreco.giddyapp.libpicker.PickerModule

@Component(modules = [CreatorModule::class, PickerModule::class])
interface CreatorComponent {
    fun viewModel(): CreatorViewModel
    fun animator(): CreatorAnimator
    fun picker(): ImagePicker
    fun plus(module: StepsModule): StepsComponent

    @Component.Builder
    interface Builder {
        @BindsInstance
        fun appContext(@AppContext context: Context): Builder

        @BindsInstance
        fun activity(activity: Activity): Builder

        @BindsInstance
        fun manager(fm: FragmentManager): Builder

        @BindsInstance
        fun horse(service: HorseService): Builder

        @BindsInstance
        fun img(loader: ImageLoader): Builder

        @BindsInstance
        fun fbApp(app: FirebaseApp): Builder

        @BindsInstance
        fun auth(auth: Authenticator): Builder

        @BindsInstance
        fun user(service: UserService): Builder

        fun module(module: CreatorModule): Builder

        fun build(): CreatorComponent
    }
}