class LruCache<K, V>(val size: Int, val evaluator: (K) -> V): (K) -> V {
    init {
        require(size >= 0)
    }
    override operator fun invoke(key: K): V {
        return evaluator(key)
    }

    fun clear() {
        TODO("Not implemented yet")
    }
}