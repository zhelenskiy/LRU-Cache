sealed class LinkedListNode<E>(val value: E) : Iterable<LinkedListNode<E>> {
    open val previous: LinkedListNode<E>? = null
    open val next: LinkedListNode<E>? = null
    override fun iterator(): Iterator<LinkedListNode<E>> = iterator {
        var current = this@LinkedListNode
        while (true) {
            yield(current)
            current = current.next ?: break
        }
    }
}

private class MutableLinkedListNode<E>(
    value: E,
    override var previous: MutableLinkedListNode<E>?,
    override var next: MutableLinkedListNode<E>?,
) : LinkedListNode<E>(value) {

    private fun merge(
        previous: MutableLinkedListNode<E>?,
        newValue: E,
        next: MutableLinkedListNode<E>?
    ): MutableLinkedListNode<E> {
        validatePair(previous, next)
        val newNode = MutableLinkedListNode(newValue, previous = previous, next = next)
        previous?.next = newNode
        next?.previous = newNode
        validatePair(previous, newNode)
        validatePair(newNode, next)
        return newNode
    }

    private fun validatePair(previous: MutableLinkedListNode<E>?, next: MutableLinkedListNode<E>?) {
        require(previous == null || previous != next)
        require(previous == null || previous.next == next)
        require(next == null || next.previous == previous)
    }

    fun addBefore(newValue: E): MutableLinkedListNode<E> = merge(previous, newValue, this)

    fun addAfter(newValue: E): MutableLinkedListNode<E> = merge(this, newValue, next)

    fun remove(): Pair<MutableLinkedListNode<E>?, MutableLinkedListNode<E>?> {
        val oldPrevious = this.previous
        val oldNext = this.next
        validatePair(oldPrevious, this)
        validatePair(this, oldNext)
        oldPrevious?.next = oldNext
        oldNext?.previous = oldPrevious
        this.previous = null
        this.next = null
        validatePair(oldPrevious, oldNext)
        validatePair(null, this)
        validatePair(this, null)
        return oldPrevious to oldNext
    }
}

/**
 * Linked list that exposes its nodes as readonly type instances
 */
internal class ExposingLinkedList<E> : Iterable<LinkedListNode<E>> {
    private var firstImpl: MutableLinkedListNode<E>? = null
    val first: LinkedListNode<E>? get() = firstImpl
    private var lastImpl: MutableLinkedListNode<E>? = null
    val last: LinkedListNode<E>? get() = lastImpl

    private fun invariant() {
        require((first == null) == (last == null))
        require(first?.previous == null)
        require(last?.next == null)
    }

    fun addAfterLast(value: E): LinkedListNode<E> =
        add(value) { lastImpl!!.addAfter(value).also { lastImpl = it } }

    private inline fun add(value: E, crossinline onNonEmpty: () -> LinkedListNode<E>): LinkedListNode<E> {
        invariant()
        val res = if (last != null) onNonEmpty() else MutableLinkedListNode(value, null, null).also {
            firstImpl = it
            lastImpl = it
        }
        invariant()
        return res
    }

    fun addBeforeFirst(value: E): LinkedListNode<E> =
        add(value) { firstImpl!!.addBefore(value).also { firstImpl = it } }

    override fun iterator(): Iterator<LinkedListNode<E>> = first?.iterator() ?: iterator { }

    fun remove(node: LinkedListNode<E>) {
        invariant()
        val newFirst = if (node == firstImpl) firstImpl?.next else firstImpl
        val newLast = if (node == lastImpl) lastImpl?.previous else lastImpl
        when (node) {
            is MutableLinkedListNode -> node.remove()
        }
        firstImpl = newFirst
        lastImpl = newLast
        invariant()
    }
}