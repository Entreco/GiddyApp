package nl.entreco.giddyapp.creator

import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentManager
import nl.entreco.giddyapp.creator.ui.crop.CropFragment
import nl.entreco.giddyapp.creator.ui.entry.*
import nl.entreco.giddyapp.creator.ui.select.SelectFragment
import nl.entreco.giddyapp.creator.ui.upload.UploadFragment
import javax.inject.Inject

class CreatorAnimator @Inject constructor(
    private val fm: FragmentManager
) {

    fun render(state: CreatorState?, done: (String) -> Unit) {
        when (state) {
            is CreatorState.Select -> replaceWith(SelectFragment(), state.toString())
            is CreatorState.Crop -> replaceWith(CropFragment(), state.toString())
            is CreatorState.EntryName -> replaceWith(EntryNameFragment(), state.toString())
            is CreatorState.EntryDescription -> replaceWith(EntryDescriptionFragment(), state.toString())
            is CreatorState.EntryGender -> replaceWith(EntryGenderFragment(), state.toString())
            is CreatorState.EntryPrice -> replaceWith(EntryPriceFragment(), state.toString())
            is CreatorState.EntryCategory -> replaceWith(EntryCategoryFragment(), state.toString())
            is CreatorState.EntryLevel -> replaceWith(EntryLevelFragment(), state.toString())
            is CreatorState.Upload -> replaceWith(UploadFragment(), state.toString())
            is CreatorState.Done -> done(state.horseId)
        }
    }

    private fun replaceWith(frag: Fragment, tag: String) {
        val transaction = fm.beginTransaction()
            .replace(R.id.createFragmentContainer, frag, tag)
        when (frag) {
            is SelectFragment -> { /* ignore */ }
            else -> transaction.addToBackStack(tag)
        }

        transaction.commit()
    }
}