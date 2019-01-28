package nl.entreco.giddyapp.creator.di

import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.creator.CreatorState

@Module
class StepsModule(private val state: CreatorState?) {

    @Provides
    fun provideSelectState() : CreatorState.Select?{
        return state as? CreatorState.Select
    }

    @Provides
    fun provideCropState() : CreatorState.Crop?{
        return state as? CreatorState.Crop
    }

    @Provides
    fun provideUploadState() : CreatorState.Upload?{
        return state as? CreatorState.Upload
    }
}