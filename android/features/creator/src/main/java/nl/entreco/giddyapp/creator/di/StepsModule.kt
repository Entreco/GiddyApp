package nl.entreco.giddyapp.creator.di

import android.content.Context
import android.view.View
import android.view.inputmethod.InputMethodManager
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.Module
import dagger.Provides
import nl.entreco.giddyapp.creator.CreatorState

@Module
class StepsModule(private val sheet: View, private val state: CreatorState?) {

    @Provides
    fun provideBottomSheet(): BottomSheetBehavior<View> {
        return BottomSheetBehavior.from(sheet)
    }

    @Provides
    fun provideInputMethodManager(context: Context) : InputMethodManager = context.getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager

    @Provides
    fun provideSelectState(): CreatorState.Select {
        if (state !is CreatorState.Select) throw IllegalStateException("state should be 'Select' but is $state")
        return state
    }

    @Provides
    fun provideCropState(): CreatorState.Crop {
        if (state !is CreatorState.Crop) throw IllegalStateException("state should be 'Crop' but is $state")
        return state
    }

    @Provides
    fun provideEntryState(): CreatorState.Entry {
        if (state !is CreatorState.Entry) throw IllegalStateException("state should be 'Entry' but is $state")
        return state
    }

    @Provides
    fun provideUploadState(): CreatorState.Upload {
        if (state !is CreatorState.Upload) throw IllegalStateException("state should be 'Upload' but is $state")
        return state
    }
}