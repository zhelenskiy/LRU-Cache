class LruCache<K, V>(val size: UInt, val evaluator: (K) -> V): (K) -> V {
    override operator fun invoke(key: K): V {
        TODO("Not implemented yet")
    }

    fun clear() {
        TODO("Not implemented yet")
    }
}