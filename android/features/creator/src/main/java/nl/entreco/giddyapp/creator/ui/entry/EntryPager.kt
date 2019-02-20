package nl.entreco.giddyapp.creator.ui.entry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import nl.entreco.giddyapp.creator.databinding.EntryDescriptionBinding
import nl.entreco.giddyapp.creator.databinding.EntryEmptyBinding
import nl.entreco.giddyapp.creator.databinding.EntryGenderBinding
import nl.entreco.giddyapp.creator.databinding.EntryNameBinding

class EntryPager(
    context: Context,
    private val items: List<Form>,
    private val nextListener: EntryListeners.OnNextPlease
) : PagerAdapter() {
    private val inflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentItem = items[position]
        val binding = when (currentItem) {
            is Form.Name -> setupName(container, currentItem)
            is Form.Desc -> setupDescription(container, currentItem)
            is Form.Gender -> setupGender(container, currentItem)
            else -> setupEmpty(container)
        }
        container.addView(binding.root)
        return binding.root
    }

    private fun setupName(container: ViewGroup, currentItem: Form.Name): EntryNameBinding =
        EntryNameBinding
            .inflate(inflater, container, false)
            .apply {
                entry = currentItem
                model = InputEntryModel(currentItem, nextListener)
            }

    private fun setupDescription(
        container: ViewGroup,
        currentItem: Form.Desc
    ): EntryDescriptionBinding =
        EntryDescriptionBinding
            .inflate(inflater, container, false)
            .apply {
                entry = currentItem
                model = InputEntryModel(currentItem, nextListener)
            }

    private fun setupGender(
        container: ViewGroup,
        currentItem: Form.Gender
    ): EntryGenderBinding = EntryGenderBinding
        .inflate(inflater, container, false)
        .apply {
            entry = currentItem
            model = InputEntryModel(currentItem, nextListener)
        }

    private fun setupEmpty(container: ViewGroup) = EntryEmptyBinding.inflate(inflater, container, false)

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as? View)
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = items.size
}