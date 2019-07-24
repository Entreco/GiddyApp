package nl.entreco.giddyapp.libcore.launch.features

import android.content.Intent

object ProfileNavigator : DynamicFeature<Intent>(
    activityName = "nl.entreco.giddyapp.profile.ProfileActivity",
    featureName = "profile",
    requestCode = PROFILE_INSTALL_CODE
)