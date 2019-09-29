package nl.entreco.giddyapp.history.models

import nl.entreco.giddyapp.history.databinding.ListMatchDetailBinding

class DetailViewHolder(private val binding: ListMatchDetailBinding) :
    BaseViewHolder<BaseModel.DetailModel>(binding.root) {
    override fun bind(model: BaseModel.DetailModel?, position: Int) {
        binding.model = model
        binding.executePendingBindings()
    }
}