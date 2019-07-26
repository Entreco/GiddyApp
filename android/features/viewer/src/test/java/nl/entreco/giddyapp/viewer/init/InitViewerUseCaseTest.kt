package nl.entreco.giddyapp.viewer.init

import android.content.Context
import com.nhaarman.mockitokotlin2.*
import nl.entreco.giddyapp.libauth.Authenticator
import nl.entreco.giddyapp.libauth.account.Account
import nl.entreco.giddyapp.libcore.Threading
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseRequest
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseResponse
import nl.entreco.giddyapp.libhorses.fetch.FetchHorseUsecase
import org.junit.Test

import org.junit.Assert.*
import org.junit.Before

class InitViewerUseCaseTest {

    private val mockDone: (InitViewerResponse) -> Unit = mock()
    private val mockFetchHorseUsecase: FetchHorseUsecase = mock()
    private val mockAuthenticator: Authenticator = mock()
    private val mockContext: Context = mock()

    private lateinit var subject : InitViewerUseCase

    @Before
    fun setUp() {
        Threading.disable()
        subject = InitViewerUseCase(mockContext, mockAuthenticator, mockFetchHorseUsecase)
    }

    @Test
    fun go() {
        subject.go(InitViewerRequest("my horse"), mockDone)
        thenSign()
        thenObserve(Account.Anomymous("uid", "name"))
        thenFetch("my horse")
        thenReturned()
    }

    private fun thenReturned() {
        val doneCaptor = argumentCaptor<InitViewerResponse>()
        verify(mockDone).invoke(doneCaptor.capture())
        assertTrue(doneCaptor.lastValue is InitViewerResponse.Initialized)
    }

    private fun thenFetch(horseId: String){
        val fetchCaptor = argumentCaptor<(FetchHorseResponse)->Unit>()
        verify(mockFetchHorseUsecase).go(eq(FetchHorseRequest(horseId)), fetchCaptor.capture())
        fetchCaptor.lastValue.invoke(FetchHorseResponse(emptyList()))
    }

    private fun thenObserve(account: Account) {
        val observeCaptor = argumentCaptor<(Account) -> Unit>()
        verify(mockAuthenticator).observe(any(), observeCaptor.capture())
        observeCaptor.lastValue.invoke(account)
    }

    private fun thenSign() {
        val signInCaptor = argumentCaptor<() -> Unit>()
        verify(mockAuthenticator).signinOrAnonymous(eq(mockContext), signInCaptor.capture())
        signInCaptor.lastValue.invoke()
    }

    @Test
    fun clear() {
    }
}