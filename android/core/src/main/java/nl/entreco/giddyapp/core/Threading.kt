package nl.entreco.giddyapp.core

import android.os.Handler
import android.os.Looper
import java.lang.ref.WeakReference
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors
import java.util.concurrent.Future

class Threading {
    companion object {
        internal var testMode: Boolean = false
        fun disable() {
            testMode = true
        }
    }
}

private val crashLogger = { throwable: Throwable -> throwable.printStackTrace() }

internal object BackgroundExecutor {
    private var executor: ExecutorService =
        Executors.newScheduledThreadPool(2 * Runtime.getRuntime().availableProcessors())

    fun <T> submit(task: () -> T): Future<T> = executor.submit(task)
}

private object ContextHelper {
    val handler = Handler(Looper.getMainLooper())
}

class AnkoAsyncContext<T>(val weakRef: WeakReference<T>)


/**
 * Execute [task] asynchronously.
 *
 * @param exceptionHandler optional exception handler.
 *  If defined, any exceptions thrown inside [task] will be passed to it. If not, exceptions will be ignored.
 * @param task the code to execute asynchronously.
 */
fun <T> T.onBg(
    exceptionHandler: ((Throwable) -> Unit)? = crashLogger,
    task: AnkoAsyncContext<T>.() -> Unit
): Boolean {
    val context = AnkoAsyncContext(WeakReference(this))
    if (Threading.testMode) context.task()
    else {
        BackgroundExecutor.submit {
            return@submit try {
                context.task()
            } catch (thr: Throwable) {
                val result = exceptionHandler?.invoke(thr)
                if (result != null) {
                    result
                } else {
                    Unit
                }
            }
        }
    }
    return true
}

/**
 * Execute [f] on the application UI thread.
 * [doAsync] receiver will be passed to [f].
 * If the receiver does not exist anymore (it was collected by GC), [f] will not be executed.
 */
fun <T> AnkoAsyncContext<T>.onUi(f: (T) -> Unit): Boolean {
    val ref = weakRef.get() ?: return false
    if (Threading.testMode || Looper.getMainLooper() === Looper.myLooper()) {
        f(ref)
    } else {
        ContextHelper.handler.post { f(ref) }
    }
    return true
}