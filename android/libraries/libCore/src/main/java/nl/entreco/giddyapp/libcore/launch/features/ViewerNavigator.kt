package nl.entreco.giddyapp.libcore.launch.features

import android.content.Intent

object ViewerNavigator : DynamicFeature<Intent>(
    activityName = "nl.entreco.giddyapp.viewer.ViewerActivity",
    featureName = "viewer",
    requestCode = VIEWER_INSTALL_CODE
)