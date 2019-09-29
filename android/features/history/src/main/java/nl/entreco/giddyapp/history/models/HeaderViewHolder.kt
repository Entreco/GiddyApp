package nl.entreco.giddyapp.history.models

import nl.entreco.giddyapp.history.databinding.ListMatchNameBinding

class HeaderViewHolder(private val binding: ListMatchNameBinding) :
    BaseViewHolder<BaseModel.HeaderModel>(binding.root) {
    override fun bind(model: BaseModel.HeaderModel?, position: Int) {
        binding.model = model
        binding.executePendingBindings()
    }
}