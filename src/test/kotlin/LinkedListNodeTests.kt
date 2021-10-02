import com.google.common.truth.Truth.assertThat
import org.junit.Test

class LinkedListNodeTests {
    private fun makeList(size: Int): LinkedListNode<Int>? {
        val nodes = Array(size) { LinkedListNode(it) }
        for ((prev, next) in nodes.indices zip nodes.indices.drop(1)) {
            nodes[prev].next = nodes[next]
            nodes[next].previous = nodes[prev]
        }
        return if (size > 0) nodes[0] else null
    }

    @Test
    fun `Iterable instance`() {
        assertThat(makeList(10)).containsExactlyElementsIn(0..9)
        assertThat(makeList(10)!!.next).containsExactlyElementsIn(1..9)
    }
}