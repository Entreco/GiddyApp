package nl.entreco.giddyapp.viewer.di

import android.content.Context
import android.content.res.Resources
import android.view.View
import com.google.android.gms.common.wrappers.InstantApps
import com.google.android.material.appbar.AppBarLayout
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Module
import dagger.Provides
import kotlinx.android.synthetic.main.search_panel.view.*
import nl.entreco.giddyapp.libcore.di.ActivityContext
import nl.entreco.giddyapp.libcore.ui.SheetBehavior
import nl.entreco.giddyapp.viewer.ViewerActivity
import nl.entreco.giddyapp.viewer.databinding.ActivityViewerBinding
import nl.entreco.giddyapp.viewer.navigation.ViewerNavigation
import nl.entreco.giddyapp.viewer.navigation.installed.InstalledViewerNavigation
import nl.entreco.giddyapp.viewer.navigation.instant.InstantViewerNavigation

@Module
class ViewerModule(private val url: String?, private val binding: ActivityViewerBinding) {

    @Provides
    @ViewerScope
    @ActivityContext
    fun provideContext(activity: ViewerActivity): Context = activity

    @Provides
    @ViewerUrl
    fun provideUrl(): String? = when {
        url.isNullOrBlank() -> null
        url == "viewer" -> null
        url == "null" -> null
        else -> url
    }

    @Provides
    @SheetBehavior
    fun provideDetailBehaviour(): BottomSheetBehavior<View> = BottomSheetBehavior.from(binding.includeSheet.sheet)

    @Provides
    @ViewerScope
    @FilterBehaviour
    fun provideFilterBehaviour(): BottomSheetBehavior<View> =
        BottomSheetBehavior.from(binding.includeSearchPanel)

    @Provides
    @ViewerScope
    fun provideNavigation(
        @ActivityContext context: Context,
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
    @ViewerScope
    fun provideAppbarLayout(): AppBarLayout {
        return binding.includeToolbarViewer.appbar
    }

    @Provides
    @ViewerScope
    fun provideResources(@ActivityContext context: Context): Resources {
        return context.resources
    }
}