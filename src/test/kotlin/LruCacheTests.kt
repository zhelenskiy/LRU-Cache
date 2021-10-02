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
}