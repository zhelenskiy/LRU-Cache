import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LruCacheTest {
    @Test
    fun `Evaluation test`() {
        fun realFunction(value: Int) = value * 2
        for (cacheSize in 0U..10U) {
            val cachedFunction = LruCache(cacheSize, ::realFunction)
            for (argument in -10..10)
                assertThat(cachedFunction(argument)).isEqualTo(realFunction(argument))
        }
    }
}