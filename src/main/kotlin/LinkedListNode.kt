class LinkedListNode<E> private constructor(
    val value: E,
    previous: LinkedListNode<E>?,
    next: LinkedListNode<E>?,
) : Iterable<E> {
    constructor(value: E): this(value, null, null)

    var previous: LinkedListNode<E>? = previous
        private set
    var next: LinkedListNode<E>? = next
        private set

    private fun merge(previous: LinkedListNode<E>?, newValue: E, next: LinkedListNode<E>?): LinkedListNode<E> {
        validatePair(previous, next)
        val newNode = LinkedListNode(newValue, previous = previous, next = next)
        previous?.next = newNode
        next?.previous = newNode
        validatePair(previous, newNode)
        validatePair(newNode, next)
        return newNode
    }

    private fun validatePair(previous: LinkedListNode<E>?, next: LinkedListNode<E>?) {
        require(previous != next)
        require(previous == null || previous.next == next)
        require(next == null || next.previous == previous)
    }

    fun addBefore(newValue: E): LinkedListNode<E> = merge(previous, newValue, this)

    fun addAfter(newValue: E): LinkedListNode<E> = merge(this, newValue, next)

    override fun iterator(): Iterator<E> = iterator {
        var current = this@LinkedListNode
        while (true) {
            yield(current.value)
            current = current.next ?: break
        }
    }
}