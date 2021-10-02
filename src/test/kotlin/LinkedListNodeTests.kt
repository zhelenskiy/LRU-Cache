import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkedListNodeTests {
    private fun makeList(size: Int): ExposingLinkedList<Int> = ExposingLinkedList<Int>().apply {
        for (value in 0 until size)
            addAfterLast(value)
    }

    private fun makeReversedList(size: Int): ExposingLinkedList<Int> = ExposingLinkedList<Int>().apply {
        for (value in 0 until size)
            addBeforeFirst(value)
    }

    @Test
    fun `Iterable instance`() {
        assertThat(makeList(0)).isEmpty()
        assertThat(makeReversedList(0)).isEmpty()
        assertThat(makeList(10).map { it.value }).containsExactlyElementsIn(0..9)
        assertThat(makeReversedList(10).map { it.value }).containsExactlyElementsIn(9 downTo 0)
    }

    @Test
    fun `Removing elements`() {
        val size = 20
        for (i in 0 until size) {
            val nodes = makeList(size)
            val removed = nodes.elementAt(i)
            nodes.remove(removed)
            assertThat(nodes.map { it.value }).containsExactlyElementsIn((0 until size) - i)
        }
    }
}