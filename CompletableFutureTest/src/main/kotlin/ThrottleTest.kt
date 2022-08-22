import kotlinx.coroutines.*
import java.lang.Runnable
import java.util.concurrent.atomic.AtomicLong

fun main() {
    val r: Runnable = ThrottleTest()
    val test = ThrottleTest.throttleFirst<Int>(5000, CoroutineScope(Dispatchers.Main)) {
        Thread(r).start()
    }
    for (t in 1..10) {
        test.run {
            println(t)
        }
    }
}

class ThrottleTest : Runnable{
    companion object {
        private val number = AtomicLong(0)

        fun <T> throttleFirst(
            skipMs: Long = 300L,
            coroutineScope: CoroutineScope,
            destinationFunction: (T) -> Unit
        ): (T) -> Unit {
            var throttleJob: Job? = null
            return { param: T ->
                if (throttleJob?.isCompleted != false) {
                    throttleJob = coroutineScope.launch {
                        destinationFunction(param)
                        delay(skipMs)
                    }
                }
            }
        }
    }
    operator fun next(): Long {
        return number.getAndIncrement()
    }

    override fun run() {
        try {
            println(next())
            Thread.sleep((2000 + (Math.random() * 5000).toInt()).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

}