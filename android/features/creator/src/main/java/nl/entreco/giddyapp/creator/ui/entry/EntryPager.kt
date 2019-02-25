package nl.entreco.giddyapp.creator.ui.entry

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewpager.widget.PagerAdapter
import kotlinx.android.synthetic.main.entry_gender.view.*
import nl.entreco.giddyapp.creator.R
import nl.entreco.giddyapp.creator.databinding.*

class EntryPager(
    context: Context,
    private val items: MutableList<Form>,
    private val nextListener: EntryListeners.OnNextPlease
) : PagerAdapter() {
    private val inflater = LayoutInflater.from(context)

    private var enteredName : String? = null
    private var enteredGender : String? = null

    override fun instantiateItem(container: ViewGroup, position: Int): Any {
        val currentItem = items[position]
        val binding = when (currentItem) {
            is Form.Name -> setupName(container, currentItem, position)
            is Form.Desc -> setupDescription(container, currentItem, position)
            is Form.Gender -> setupGender(container, currentItem, position)
            is Form.Level -> setupLevel(container, currentItem, position)
            is Form.Price -> setupPrice(container, currentItem, position)
            is Form.Category -> setupCategory(container, currentItem, position)
            else -> setupEmpty(container)
        }
        container.addView(binding.root)
        return binding.root
    }

    private fun setupName(
        container: ViewGroup,
        currentItem: Form.Name,
        position: Int
    ): EntryNameBinding =
        EntryNameBinding
            .inflate(inflater, container, false)
            .apply {
                entry = currentItem
                model = InputEntryModel(currentItem) { update ->
                    items[position] = update
                    enteredName = (update as Form.Name).input.get()
                    notifyDataSetChanged()
                    nextListener.onNext(update)
                }
            }

    private fun setupDescription(
        container: ViewGroup,
        currentItem: Form.Desc,
        position: Int
    ): EntryDescriptionBinding =
        EntryDescriptionBinding
            .inflate(inflater, container, false)
            .apply {
                entry = currentItem
                name = enteredName
                model = InputEntryModel(currentItem) { update ->
                    items[position] = update
                    nextListener.onNext(update)
                }
            }

    private fun setupGender(
        container: ViewGroup,
        currentItem: Form.Gender,
        position: Int
    ): EntryGenderBinding = EntryGenderBinding
        .inflate(inflater, container, false)
        .apply {
            entry = currentItem
            name = "$enteredName's"
            model = InputEntryModel(currentItem) { update ->
                items[position] = update
                enteredGender = when((update as Form.Gender).checked.get()){
                    R.id.mare -> "her"
                    else -> "his"
                }
                notifyDataSetChanged()
                nextListener.onNext(update)
            }
        }

    private fun setupLevel(
        container: ViewGroup,
        currentItem: Form.Level,
        position: Int
    ): EntryLevelBinding = EntryLevelBinding
        .inflate(inflater, container, false)
        .apply {
            entry = currentItem
            name = enteredName
            model = InputEntryModel(currentItem) { update ->
                items[position] = update
                nextListener.onNext(update)
            }
        }

    private fun setupPrice(
        container: ViewGroup,
        currentItem: Form.Price,
        position: Int
    ): EntryPriceBinding = EntryPriceBinding
        .inflate(inflater, container, false)
        .apply {
            entry = currentItem
            gender = enteredGender
            model = InputEntryModel(currentItem) { update ->
                items[position] = update
                nextListener.onNext(update)
            }
        }

    private fun setupCategory(
        container: ViewGroup,
        currentItem: Form.Category,
        position: Int
    ): EntryCategoryBinding = EntryCategoryBinding
        .inflate(inflater, container, false)
        .apply {
            entry = currentItem
            model = InputEntryModel(currentItem) { update ->
                items[position] = update
                nextListener.onNext(update)
            }
        }

    private fun setupEmpty(container: ViewGroup) = EntryEmptyBinding.inflate(inflater, container, false)

    override fun destroyItem(container: ViewGroup, position: Int, obj: Any) {
        container.removeView(obj as? View)
    }

    override fun isViewFromObject(view: View, obj: Any) = view == obj

    override fun getCount() = items.size
}