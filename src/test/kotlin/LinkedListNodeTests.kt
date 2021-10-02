import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkedListNodeTests {
    private fun makeList(size: Int): LinkedListNode<Int>? =
        generateSequence(LinkedListNode(0).takeIf { size > 0 }) { it.addAfter(it.value + 1) }
            .take(size).toList().firstOrNull()

    private fun makeReversedList(size: Int): LinkedListNode<Int>? =
        generateSequence(LinkedListNode(0).takeIf { size > 0 }) { it.addBefore(it.value + 1) }.take(size).lastOrNull()

    @Test
    fun `Iterable instance`() {
        assertThat(makeList(0)).isNull()
        assertThat(makeReversedList(0)).isNull()
        assertThat(makeList(10)).containsExactlyElementsIn(0..9)
        assertThat(makeReversedList(10)).containsExactlyElementsIn(9 downTo 0)
        assertThat(makeList(10)!!.next).containsExactlyElementsIn(1..9)
    }

    @Test
    fun `Removing elements`() {
        val size = 20
        for (i in 0 until size) {
            val nodes = generateSequence(makeList(size)) { it.next }.toList()
            assertThat(nodes[i].remove()).isEqualTo(i)
            if (i == 0)
                assertThat(nodes[0]).hasSize(1)
            else
                assertThat(nodes[0]).containsExactlyElementsIn((0 until size) - i)
        }
    }
}