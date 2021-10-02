import org.junit.Test

class LruCacheTest {
    @Test
    fun `Instance test`() {
        LruCache<Int, Unit>(0U) { }
    }
}