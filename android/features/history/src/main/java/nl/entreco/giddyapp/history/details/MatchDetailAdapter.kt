package nl.entreco.giddyapp.history.details

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.DiffUtil
import androidx.recyclerview.widget.ListAdapter
import nl.entreco.giddyapp.history.R
import nl.entreco.giddyapp.history.databinding.ListMatchDescriptionBinding
import nl.entreco.giddyapp.history.databinding.ListMatchDetailBinding
import nl.entreco.giddyapp.history.databinding.ListMatchNameBinding
import nl.entreco.giddyapp.history.models.*
import javax.inject.Inject

class MatchDetailAdapter @Inject constructor() : ListAdapter<BaseModel, BaseViewHolder<*>>(differ) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): BaseViewHolder<*> {
        val inflater = LayoutInflater.from(parent.context)
        return when (viewType) {
            R.layout.list_match_name -> HeaderViewHolder(ListMatchNameBinding.inflate(inflater, parent, false))
            R.layout.list_match_description -> DescriptionViewHolder(ListMatchDescriptionBinding.inflate(inflater, parent, false))
            R.layout.list_match_detail -> DetailViewHolder(ListMatchDetailBinding.inflate(inflater, parent, false))
            else -> throw IllegalArgumentException("Unsupported viewType: $viewType")
        }
    }

    override fun getItemViewType(position: Int): Int = when (val item = getItem(position)) {
        is BaseModel.DetailModel -> R.layout.list_match_detail
        is BaseModel.HeaderModel -> R.layout.list_match_name
        is BaseModel.DescriptionModel -> R.layout.list_match_description
        else -> throw IllegalArgumentException("Unsupported viewType: $item")
    }

    override fun onBindViewHolder(holder: BaseViewHolder<*>, position: Int) {
        when (holder) {
            is DetailViewHolder -> holder.bind(getItem(position) as BaseModel.DetailModel, position)
            is DescriptionViewHolder -> holder.bind(getItem(position) as BaseModel.DescriptionModel, position)
            is HeaderViewHolder -> holder.bind(getItem(position) as BaseModel.HeaderModel, position)
        }
    }
}

private val differ = object : DiffUtil.ItemCallback<BaseModel>() {
    override fun areItemsTheSame(oldItem: BaseModel, newItem: BaseModel) =
        oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: BaseModel, newItem: BaseModel) =
        oldItem == newItem

}