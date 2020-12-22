open class LRUCacheImpl<K, V>(private val capacity: Int) : LRUCache<K, V> {
    private data class ListNode<K, V>(val key: K, var value: V, var next: ListNode<K, V>?, var prev: ListNode<K, V>?)

    private var head: ListNode<K, V>? = null
    private var tail: ListNode<K, V>? = null
    private val mapper: MutableMap<K, ListNode<K, V>> = mutableMapOf()

    init {
        require(capacity > 0) { "Wrong capacity size" }
    }

    override fun getSize(): Int {
        val curSize = mapper.size
        assert(curSize in 0..capacity)
        return curSize
    }


    override fun containsKey(key: K): Boolean {
        return mapper.contains(key)
    }

    override fun get(key: K): V? {
        val oldSize = getSize()
        val result = getImpl(key)
        val size = getSize()

        assert(oldSize == size && result == mapper[key]?.value)
        assert((size > 0) xor (head == null && tail == null))
        assert(!mapper.containsKey(key) || mapper.containsKey(key) && head?.key == key)
        return result
    }

    override fun put(key: K, value: V): V? {
        val oldSize = getSize()
        val wasIn = mapper.containsKey(key)
        val wasFull = getSize() == capacity

        val result = putImpl(key, value)

        val size = getSize()
        assert((size > 0) xor (head == null && tail == null))
        assert(size in 1..capacity)
        assert(
            wasIn && oldSize == size
                    || !wasIn && !wasFull && oldSize + 1 == size
                    || !wasIn && wasFull && oldSize == size
        )
        assert(mapper[key]?.value == value)
        assert(head?.key == key && head?.value == value)

        return result
    }

    override fun remove(key: K): V? {
        val oldSize = getSize()
        val wasIn = mapper.containsKey(key)

        val result = removeImpl(key)

        val size = getSize()
        val isIn = mapper.containsKey(key)

        assert(!isIn)
        assert(
            wasIn && oldSize == size + 1
                    || !wasIn && oldSize == size
        )
        assert((size > 0) xor (head == null && tail == null))
        assert(size in 0..capacity)

        return result
    }

    override fun iterator(): Iterator<Pair<K, V>> {
        return object : Iterator<Pair<K, V>> {
            private var currentNode: ListNode<K, V>? = head

            override fun hasNext(): Boolean {
                return currentNode != null
            }

            override fun next(): Pair<K, V> {
                if (!hasNext()) {
                    throw NoSuchElementException("Iterator next element not found")
                }
                val result = Pair(currentNode!!.key, currentNode!!.value)
                currentNode = currentNode!!.next
                return result
            }
        }
    }

    private fun removeNode(node: ListNode<K, V>) {
        val prevNode = node.prev
        val nextNode = node.next

        // Remove links
        if (prevNode != null) {
            prevNode.next = nextNode
        }
        if (nextNode != null) {
            nextNode.prev = prevNode
        }
        // Trace head and tail
        if (node == head) {
            head = node.next
        }
        if (node == tail) {
            tail = node.prev
        }
    }

    private fun pushNode(node: ListNode<K, V>) {
        if (head == null) {
            tail = node
        } else {
            head!!.prev = node
        }
        head = node
    }

    private fun setOnTopNode(node: ListNode<K, V>) {
        node.prev = null
        node.next = head
        pushNode(node)
    }

    private fun assertNonEmptyList() {
        assert(capacity > 0)
        assert(head != null)
        assert(tail != null)
        assert(getSize() > 0)
    }

    private fun getImpl(key: K): V? {
        val resultNode = mapper[key] ?: return null
        assertNonEmptyList()
        removeNode(resultNode)
        setOnTopNode(resultNode)

        return resultNode.value
    }

    private fun putImpl(key: K, value: V): V? {
        val resultNode = mapper[key]
        if (resultNode != null) {
            assertNonEmptyList()
            val resValue = resultNode.value
            removeNode(resultNode)
            resultNode.value = value
            setOnTopNode(resultNode)

            return resValue
        } else {
            if (getSize() == capacity) {
                assertNonEmptyList()
                val lastNode = tail!!
                removeNode(lastNode)
                val removedValue = mapper.remove(lastNode.key)?.value
                // check for delete last usage value
                assert(removedValue == lastNode.value)
                val newNode = ListNode(key, value, prev = null, next = head)
                pushNode(newNode)
                mapper[key] = newNode
            } else {
                val newNode = ListNode(key, value, prev = null, next = head)
                pushNode(newNode)
                mapper[key] = newNode
            }
        }

        return null
    }

    private fun removeImpl(key: K): V? {
        val resultNode = mapper[key] ?: return null
        assert(resultNode.key == key)
        assertNonEmptyList()
        removeNode(resultNode)
        val result = resultNode.value
        val removedValue = mapper.remove(key)?.value
        assert(removedValue == result)

        return result
    }
}