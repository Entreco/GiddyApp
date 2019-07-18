package nl.entreco.giddyapp.libcore.launch.features

import android.content.Intent

object CreatorNavigator : DynamicFeature<Intent>(
    activityName = "nl.entreco.giddyapp.creator.CreatorActivity",
    featureName = "creator"
)