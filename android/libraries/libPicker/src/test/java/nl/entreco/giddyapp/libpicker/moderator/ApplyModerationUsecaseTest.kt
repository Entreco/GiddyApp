package nl.entreco.giddyapp.libpicker.moderator

import android.content.Context
import android.net.Uri
import com.nhaarman.mockitokotlin2.argumentCaptor
import com.nhaarman.mockitokotlin2.eq
import com.nhaarman.mockitokotlin2.mock
import com.nhaarman.mockitokotlin2.verify
import nl.entreco.giddyapp.libcore.Threading
import nl.entreco.giddyapp.libpicker.ImageLabeller
import org.junit.Before
import org.junit.Test

class ApplyModerationUsecaseTest {

    private val mockLabeller: ImageLabeller = mock()
    private val mockUri: Uri = mock()
    private val mockContext: Context = mock()
    private val mockDone: (ApplyModerationResponse)->Unit = mock()

    private lateinit var subject : ApplyModerationUsecase

    private var givenAllowedLabels = emptyList<String>()
    private var givenBlockedLabels = emptyList<String>()
    private val labelCaptor = argumentCaptor<(List<String>)->Unit>()

    @Before
    fun setUp() {
        Threading.disable()
        givenSubject()
    }

    @Test
    fun `it should allow images labelled with 'horse'`() {
        whenModdingImage("HorsE")
        thenModerationIs(Moderation.Allowed)
    }

    @Test
    fun `it should block images labelled with 'porn'`() {
        givenBlocked("porn")
        whenModdingImage("porn")
        thenModerationIs(Moderation.Rejected("porn", ""))
    }

    @Test
    fun `it should list all reasons for blocking`() {
        givenBlocked("porn", "violence", "gambling")
        whenModdingImage("porn", "gambling")
        thenModerationIs(Moderation.Rejected("porn|gambling"))
    }

    @Test
    fun `it should allow images labelled with 'horse' when 'porn' is blocked`() {
        givenRequired("horse")
        givenBlocked("porn")
        whenModdingImage("horse")
        thenModerationIs(Moderation.Allowed)
    }

    @Test
    fun `it should allow all images when no required and no blocks defined`() {
        whenModdingImage("all", "labels", "should", "be", "allowed")
        thenModerationIs(Moderation.Allowed)
    }

    @Test
    fun `it should not allow images missing required items`() {
        givenRequired("required")
        whenModdingImage("all", "labels", "should", "be", "allowed")
        thenModerationIs(Moderation.Rejected("", "required"))
    }

    private fun givenSubject() {
        subject = ApplyModerationUsecase(mockLabeller)
    }

    private fun givenRequired(vararg allowed: String) {
        givenAllowedLabels = allowed.toList()
    }

    private fun givenBlocked(vararg blocked: String) {
        givenBlockedLabels = blocked.toList()
    }

    private fun whenModdingImage(vararg labels: String) {
        subject.go(ApplyModerationRequest(mockContext, mockUri, givenAllowedLabels, givenBlockedLabels), mockDone)
        verify(mockLabeller).getLabels(eq(mockContext), eq(mockUri), labelCaptor.capture())
        labelCaptor.lastValue.invoke(labels.toList())
    }

    private fun thenModerationIs(result: Moderation) {
        verify(mockDone).invoke(ApplyModerationResponse(result))
    }
}