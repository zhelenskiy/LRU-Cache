internal class LinkedListNode<E>(
    val value: E,
    var previous: LinkedListNode<E>? = null,
    var next: LinkedListNode<E>? = null,
): Iterable<E> {

    override fun iterator(): Iterator<E> = iterator {
        var current = this@LinkedListNode
        while (true) {
            yield(current.value)
            current = current.next ?: break
        }
    }
}