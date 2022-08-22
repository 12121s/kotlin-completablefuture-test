import java.util.concurrent.CountDownLatch
import java.util.concurrent.TimeUnit
import java.util.concurrent.atomic.AtomicLong

fun main(args: Array<String>) {
    CountDownLatchTest.main(args)
}

class CountDownLatchTest(private val latch: CountDownLatch) : Runnable {
    operator fun next(): Long {
        return number.getAndIncrement()
    }

    override fun run() {
        try {
            println(next())
            Thread.sleep((2000 + (Math.random() * 5000).toInt()).toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        } finally {
            latch.countDown()
        }
    }

    companion object {
        private val number = AtomicLong(0)
        @JvmStatic
        fun main(args: Array<String>) {
            for (threadNo in 0..10) {
                val latch = CountDownLatch(1)
                val t: Runnable = CountDownLatchTest(latch)
                Thread(t).start()
                try {
                    latch.await(5, TimeUnit.SECONDS)
                } catch (e: InterruptedException) {
                    e.printStackTrace()
                }
            }
        }
    }
}