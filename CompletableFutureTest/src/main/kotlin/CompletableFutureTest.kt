import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    /* given */
    val message = "Hello";
    val messageFuture = CompletableFutureTest().sayMessage(message)

    /* when */
    try {
        val result = messageFuture.get(5, TimeUnit.SECONDS)
        println("result= $result")
    } catch (e: Exception) {
        println("timeout=  ${e.message}")
    }
}

class CompletableFutureTest {

    fun sayMessage(message: String) : CompletableFuture<String> {
//        sleepOneSecond()


        val future = CompletableFuture<String>()

        CompletableFuture.delayedExecutor(5, TimeUnit.SECONDS).execute {
            val saidMessage = "Say $message"
            println("Said Message = {$saidMessage}")
            future.complete(saidMessage)
        }
        /*val async = CompletableFuture.runAsync {
            sleepOneSecond()
            val saidMessage = "Say $message"
            println("Said Message = {$saidMessage}")
            future.complete(saidMessage)
        }*/
        return future
    }

    private fun sleepOneSecond() {
        try {
            println("start to sleep 1 second.")
            Thread.sleep(6000)
            println("end to sleep 1 second.")
        } catch (e :InterruptedException) {
            throw IllegalStateException()
        }
    }
}