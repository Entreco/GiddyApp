package nl.entreco.giddyapp.creator.di

import android.view.View
import dagger.Module
import dagger.Provides

@Module
class CreatorModule(private val sheet: View){
    @Provides
    fun provideSheet(): View {
        return sheet
    }
}