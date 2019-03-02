package nl.entreco.giddyapp.libhorses.horses.fetch

import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import nl.entreco.giddyapp.libcore.Threading
import nl.entreco.giddyapp.libhorses.Horse
import nl.entreco.giddyapp.libhorses.HorseService
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseResponse
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseUsecase
import org.junit.Before
import org.junit.Test

class FetchHorseUsecaseTest {
    private val mockService: HorseService = mock {}
    private val mockDone: (FetchHorseResponse) -> Unit = mock {}
    private lateinit var subject: FetchHorseUsecase
    private val doneCaptor = argumentCaptor<(List<Horse>) -> Unit>()

    @Before
    fun setUp() {
        Threading.disable()
        givenSubject()
    }

    @Test
    fun `it should generate list with 1 request id`() {
        whenFetchingHorses("santa")
        thenRequestIdsAre("santa", "", "", "", "", "", "", "", "", "")
    }

    @Test
    fun `it should generate list with randoms (null)`() {
        whenFetchingHorses()
        thenRequestIdsAre("", "", "", "", "", "", "", "", "", "")
    }

    @Test
    fun `it should generate list with randoms (empty)`() {
        whenFetchingHorses("")
        thenRequestIdsAre("", "", "", "", "", "", "", "", "", "")
    }

    @Test
    fun `it should generate list with randoms (blank)`() {
        whenFetchingHorses(" ")
        thenRequestIdsAre("", "", "", "", "", "", "", "", "", "")
    }

    private fun givenSubject() {
        subject = FetchHorseUsecase(mockService)
    }

    private fun whenFetchingHorses(horse: String? = null) {
        subject.go(FetchHorseRequest(horse), mockDone)
    }

    private fun thenRequestIdsAre(vararg ids: String) {
        verify(mockService).fetch(eq(ids.toList()), doneCaptor.capture())
    }
}