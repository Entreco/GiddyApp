package nl.entreco.giddyapp.libhorses.swap

import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorseDetail
import nl.entreco.giddyapp.libhorses.fetch.*
import java.util.*
import javax.inject.Inject

class SwapHorseUsecase @Inject constructor(
    private val fetchImageUsecase: FetchImageUsecase,
    private val fetchHorseUsecase: FetchHorseUsecase
) {

    private val horses = mutableListOf<Horse>()
    private val queue = ArrayDeque<String>()
    private lateinit var filterOptions: FilterOptions
    var onPreloadListener: PreloadListener? = null

    fun initWith(collection: List<Horse>, filterOptions: FilterOptions) {
        this.filterOptions = filterOptions
        val atLeastOne = if (collection.isEmpty()) listOf(Horse.none()) else collection
        horses.addAll(atLeastOne)
        queue.clear()
        queue.addAll(atLeastOne.map { it.imageRef })
        preloadImages(atLeastOne)
    }

    fun go(done: (Horse, Horse) -> Unit) {
        done(pop(), peek())
    }

    private fun pop(): Horse {
        ensure()
        val popped = queue.pop()
        return horses.first { it.imageRef == popped }
    }

    private fun peek(): Horse {
        ensure()

        val peeked = queue.peek()
        return horses.first { it.imageRef == peeked }
    }

    private fun ensure() {
        if (horses.isEmpty()) throw IllegalStateException("need to add horses first")
        if (queue.isEmpty()) {
            initWith(emptyList(), filterOptions)
            fetchHorseUsecase.go(FetchHorseRequest(filterOptions = filterOptions)) { response ->
                initWith(response.horses, filterOptions)
            }
        }
    }

    private fun preloadImages(collection: List<Horse>) {
        collection.filter { it.imageUri == null }.forEach { horse ->
            fetchImageUsecase.go(
                FetchImageRequest(
                    horse.imageRef,
                    horse.imageExt
                )
            ) { response ->
                val index = horses.indexOfFirst { it.imageRef == response.imageRef }
                val updated = horses[index].copy(imageUri = response.image)
                horses[index] = updated
                onPreloadListener?.onImageReady(updated)
            }
        }
    }

    fun improve(detail: HorseDetail, like: Boolean) = filterOptions.improve(detail, like)

    interface PreloadListener {
        fun onImageReady(horse: Horse)
    }
}