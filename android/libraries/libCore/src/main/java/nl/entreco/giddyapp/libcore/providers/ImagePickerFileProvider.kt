package nl.entreco.giddyapp.libcore.providers

import androidx.core.content.FileProvider

/**
 * FileProvider for libPicker
 * -> needs to be placed in libCore because :
 *
 * You should note, however, the platform experiences the following restrictions to accessing contents of a module prior to an app restart:
 * - The platform can not apply any new manifest entries introduced by the module.
 * - The platform can not access the module’s resources for system UI components, such as notifications.
 *
 * If you need to use such resources immediately, consider including those resource in the base module of your app.
 * @see https://developer.android.com/guide/app-bundle/playcore#access_downloaded_modules
 */
class ImagePickerFileProvider : FileProvider()