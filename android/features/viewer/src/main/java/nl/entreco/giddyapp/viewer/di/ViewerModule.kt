package nl.entreco.giddyapp.viewer.di

import android.app.Activity
import android.content.Context
import android.content.res.Resources
import android.media.SoundPool
import android.view.View
import com.google.android.gms.common.wrappers.InstantApps
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.viewer.data.SoundPoolService
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.domain.sound.SoundService
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.navigation.installed.InstalledViewerNavigation
import nl.entreco.giddyapp.viewer.navigation.instant.InstantViewerNavigation

@Module
class ViewerModule(private val url: String?, private val binding: ActivityViewerBinding) {

    @Provides
    fun provideContext(activity: Activity): Context = activity

    @Provides
    @ViewerUrl
    fun provideUrl(): String? = when {
        url.isNullOrBlank() -> null
        url == "viewer" -> null
        url == "null" -> null
        else -> url
    }

    @Provides
    fun provideSheet(): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(binding.includeSheet.sheet)
    }

    @Provides
    fun provideInstantNavigation(activity: Activity): InstantViewerNavigation = InstantViewerNavigation(activity)

    @Provides
    fun provideInstalledNavigation(activity: Activity): InstalledViewerNavigation = InstalledViewerNavigation(activity)

    @Provides
    fun provideNavigation(
        context: Context,
        instant: InstantViewerNavigation,
        installed: InstalledViewerNavigation
    ): ViewerNavigation {
        return if (InstantApps.isInstantApp(context)) {
            instant
        } else {
            installed
        }
    }

    @Provides
    fun provideAppbarLayout(): AppBarLayout {
        return binding.includeToolbarViewer.appbar
    }

    @Provides
    fun provideResources(context: Context): Resources {
        return context.resources
    }

    @Provides
    fun provideSoundService(context: Context): SoundService {
        val pool = SoundPool.Builder().setMaxStreams(2).build()
        return SoundPoolService(context, pool)
    }
}