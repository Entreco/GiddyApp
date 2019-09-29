package nl.entreco.giddyapp.profile.matches

import androidx.databinding.ObservableField
import nl.entreco.giddyapp.history.R
import nl.entreco.giddyapp.history.models.BaseModel
import nl.entreco.giddyapp.libhorses.*

class MatchDetailModel {

    val ratio = ObservableField<HorseRatio>()
    val since = ObservableField<HorsePosted>()

    fun fill(horse: HorseDetail) {
        items = listOf(
            BaseModel.HeaderModel.Txt(R.string.match_description, horse.desc),
            BaseModel.DescriptionModel.Res(R.string.match_details),
            BaseModel.DetailModel.Res(R.string.match_detail_1, horse.gender.icon(), horse.gender.name),
            BaseModel.DetailModel.Res(R.string.match_detail_2, horse.category.icon(), horse.category.name),
            BaseModel.DetailModel.Res(R.string.match_detail_3, R.drawable.ic_price, horse.price.name),
            BaseModel.DetailModel.Res(R.string.match_detail_4, R.drawable.ic_location, horse.location.name)
        )
        ratio.set(horse.ratio)
        since.set(horse.since)
    }

    fun items() : List<BaseModel> = items

    private var items = listOf(
        BaseModel.HeaderModel.Loading(R.string.match_description),
        BaseModel.DescriptionModel.Res(R.string.match_details),
        BaseModel.DetailModel.Loading(R.string.match_detail_1),
        BaseModel.DetailModel.Loading(R.string.match_detail_2),
        BaseModel.DetailModel.Loading(R.string.match_detail_3),
        BaseModel.DetailModel.Loading(R.string.match_detail_4)
    )
}