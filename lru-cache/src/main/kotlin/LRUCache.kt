interface LRUCache<K, V> : Iterable<Pair<K, V>> {
    /**
     * Returns the number of key/value pairs in the map.
     */
    public fun getSize(): Int

    /**
     * Returns `true` if the map is empty (contains no elements), `false` otherwise.
     */
    public fun isEmpty() = getSize() == 0

    /**
     * Returns `true` if the map contains the specified [key].
     */
    public fun containsKey(key: K): Boolean

    /**
     * Returns the value corresponding to the given [key], or `null` if such a key is not present in the map.
     */
    public fun get(key: K): V?

    /**
     * Associates the specified [value] with the specified [key] in the map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    public fun put(key: K, value: V): V?

    /**
     * Removes the specified key and its corresponding value from this map.
     *
     * @return the previous value associated with the key, or `null` if the key was not present in the map.
     */
    public fun remove(key: K): V?

    /**
     * Gets iterator, that can be used to traverse cache content
     * @return iterator to traverse cache as a collection of key-value pairs
     */
    override fun iterator(): Iterator<Pair<K, V>>
}