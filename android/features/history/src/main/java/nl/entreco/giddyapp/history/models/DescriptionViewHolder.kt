package nl.entreco.giddyapp.history.models

import nl.entreco.giddyapp.history.databinding.ListMatchDescriptionBinding

class DescriptionViewHolder(private val binding: ListMatchDescriptionBinding) :
    BaseViewHolder<BaseModel.DescriptionModel>(binding.root) {
    override fun bind(model: BaseModel.DescriptionModel?, position: Int) {
        binding.model = model
        binding.executePendingBindings()
    }
}