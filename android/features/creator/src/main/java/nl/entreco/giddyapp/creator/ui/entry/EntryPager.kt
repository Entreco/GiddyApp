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

class EntryPager(context: Context, private val items: List<Form>) : PagerAdapter() {
    private val inflater = LayoutInflater.from(context)

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentItem = items[position]
        val binding = when (currentItem) {
            is Form.Name -> EntryNameBinding.inflate(inflater, container, false).apply { entry = currentItem }
            is Form.Desc -> EntryDescriptionBinding.inflate(inflater, container, false).apply { entry = currentItem }
            is Form.Gender -> EntryGenderBinding.inflate(inflater, container, false).apply { entry = currentItem }
            else -> EntryEmptyBinding.inflate(inflater, container, false)
        }
        container.addView(binding.root)
        return binding.root
    }

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as? View)
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = items.size
}