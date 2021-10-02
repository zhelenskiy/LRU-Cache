import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkedListNodeTests {
    private fun makeList(size: Int): LinkedListNode<Int>? {
        if (size == 0) return null
        val first = LinkedListNode(0)
        generateSequence(first) { it.addAfter(it.value + 1) }.take(size).toList()
        return first
    }

    @Test
    fun `Iterable instance`() {
        assertThat(makeList(10)).containsExactlyElementsIn(0..9)
        assertThat(makeList(10)!!.next).containsExactlyElementsIn(1..9)
    }
}