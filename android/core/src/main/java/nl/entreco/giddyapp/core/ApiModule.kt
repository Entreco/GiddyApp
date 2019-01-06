package nl.entreco.giddyapp.core

import android.content.Context
import android.util.Log
import dagger.Module
import dagger.Provides

@Module
class ApiModule{
    @Provides
    fun provideApi(context: Context) : Api {
        Log.i("DAGGER", "provideApi")
        return Api(context)
    }
}