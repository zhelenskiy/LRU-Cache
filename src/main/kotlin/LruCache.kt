class LruCache<K, V>(val size: Int, val evaluator: (K) -> V): (K) -> V {
    init {
        require(size >= 0)
    }
    private val keyValueStorage: MutableMap<K, LinkedListNode<Pair<K, V>>> = mutableMapOf()
    private val list: ExposingLinkedList<Pair<K, V>> = ExposingLinkedList()

    override operator fun invoke(key: K): V = when(val found = keyValueStorage[key]) {
        null -> {
            val newValue = evaluator(key)
            if (size > 0) {
                if (size == keyValueStorage.size) {
                    val last = list.last!!
                    list.remove(last)
                    keyValueStorage.remove(last.value.first)
                }
                keyValueStorage[key] = list.addBeforeFirst(key to newValue)
            }
            newValue
        }
        else -> {
            list.remove(found)
            list.addBeforeFirst(found.value)
            found.value.second
        }
    }
}