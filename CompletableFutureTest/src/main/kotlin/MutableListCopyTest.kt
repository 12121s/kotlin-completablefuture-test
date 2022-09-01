import java.util.concurrent.CompletableFuture
import java.util.concurrent.Executor
import java.util.concurrent.TimeUnit

fun main(args: Array<String>) {
    val copyTestList =  MutableListCopyTest()
    // data set
    val testData = mutableMapOf<Int, String>(1 to "abc" , 2 to "def", 3 to "ghi")
    copyTestList.setTestList(testData.entries.map {
        TestModel(it.key.toString(), it.value)
    })
    println("copyTestList.getTestList() : ${copyTestList.getTestList()}")

    val newTestList = copyTestList.getTestList()?.map { it?.copy() }
    newTestList?.map { test ->
        test?.apply {
            message = "list"
        }
    }
    println("copyTestList.getTestList() : ${copyTestList.getTestList()}")
    if (newTestList != null) {
        copyTestList.setTestList(newTestList)
    }
    println("copyTestList.getTestList() : ${copyTestList.getTestList()}")

    val testMutableList = copyTestList.getTestList()?.toMutableList()
    val newTestMutableList = testMutableList?.map { test ->
        test?.apply {
            message = "mutable"
        }
    }
    println("copyTestList.getTestList() : ${copyTestList.getTestList()}")
    if (newTestMutableList != null) {
        copyTestList.setTestList(newTestMutableList)
    }
    println("copyTestList.getTestList() : ${copyTestList.getTestList()}")
}

class MutableListCopyTest {
    private var testList : List<TestModel?>? = null

    fun setTestList(list: List<TestModel?>) {
        testList = null
        testList = list
    }

    fun getTestList() : List<TestModel?>? {
        return testList
    }

}

data class TestModel(
    var code: String,
    var message: String
)
