import com.google.common.truth.Truth.assertThat
import org.junit.Assert.assertThrows
import org.junit.Test

class LruCacheTests {
    @Test
    fun `Evaluation test`() {
        fun realFunction(value: Int) = value * 2
        for (cacheSize in -5..-1)
            assertThrows(IllegalArgumentException::class.java) { LruCache(cacheSize, ::realFunction) }
        for (cacheSize in 0..10) {
            val cachedFunction = LruCache(cacheSize, ::realFunction)
            for (argument in -10..10)
                assertThat(cachedFunction(argument)).isEqualTo(realFunction(argument))
        }
    }

    private fun countingTest(size: Int, expectedCalls: Int, keys: IntArray) {
        var counter = 0
        val cache = LruCache(size) { _: Int -> counter++ }
        for (key in keys)
            cache(key)
        assertThat(counter).isEqualTo(expectedCalls)
    }

    @Test
    fun `Cache of capacity 0`() {
        countingTest(size = 0, expectedCalls = 5, keys = IntArray(5))
        countingTest(size = 0, expectedCalls = 5, keys = IntArray(5) { it })
        countingTest(size = 0, expectedCalls = 6, keys = IntArray(6) { it / 3 })
        countingTest(size = 0, expectedCalls = 9, keys = IntArray(9) { it / 3 })
    }

    @Test
    fun `Cache of capacity 1`() {
        countingTest(size = 1, expectedCalls = 1, keys = IntArray(5))
        countingTest(size = 1, expectedCalls = 5, keys = IntArray(5) { it })
        countingTest(size = 1, expectedCalls = 2, keys = IntArray(6) { it / 3 })
        countingTest(size = 1, expectedCalls = 3, keys = intArrayOf(0, 1, 0))
        countingTest(size = 1, expectedCalls = 4, keys = intArrayOf(0, 1, 2, 0))
        countingTest(size = 1, expectedCalls = 4, keys = intArrayOf(0, 1, 2, 1))
    }

    @Test
    fun `Cache of capacity 2`() {
        countingTest(size = 2, expectedCalls = 1, keys = IntArray(5))
        countingTest(size = 2, expectedCalls = 5, keys = IntArray(5) { it })
        countingTest(size = 2, expectedCalls = 2, keys = IntArray(6) { it / 3 })
        countingTest(size = 2, expectedCalls = 2, keys = intArrayOf(0, 1, 0))
        countingTest(size = 2, expectedCalls = 3, keys = intArrayOf(0, 1, 2, 1))
        countingTest(size = 2, expectedCalls = 4, keys = intArrayOf(0, 1, 2, 0))
    }
}