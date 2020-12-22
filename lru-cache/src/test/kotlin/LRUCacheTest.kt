import org.junit.Test
import org.junit.Assert.*
import kotlin.random.Random

class LRUCacheTest {
    @Test
    fun emptyTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(5)
        assertEquals(cache.getSize(), 0)
        assertEquals(cache.isEmpty(), true)
    }

    @Test(expected = IllegalArgumentException::class)
    fun incorrectCapacityTest() {
        LRUCacheImpl<Int, String>(-69)
    }

    @Test(expected = NoSuchElementException::class)
    fun iteratorTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(5)
        cache.put(1, "1")
        cache.put(3, "3")
        cache.put(5, "5")
        val iter = cache.iterator()
        val list = listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1"))
        var currentId = 0
        while (true) {
            val cur = iter.next()
            assertTrue(list[currentId++] == cur)
        }
    }

    @Test
    fun putTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(228)
        assertTrue(cache.iterator().asSequence().toList() == listOf<Pair<Int, String>>())
        cache.put(1, "1")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(1, "1")))
        cache.put(2, "2")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(2, "2"), Pair(1, "1")))
        cache.put(3, "3")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(3, "3"), Pair(2, "2"), Pair(1, "1")))
    }

    @Test
    fun getTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(228)
        cache.put(1, "1")
        cache.put(3, "3")
        cache.put(5, "5")

        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.get(2), null)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.get(3), "3")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(3, "3"), Pair(5, "5"), Pair(1, "1")))
        assertEquals(cache.get(4), null)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(3, "3"), Pair(5, "5"), Pair(1, "1")))
        assertEquals(cache.get(1), "1")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(1, "1"), Pair(3, "3"), Pair(5, "5")))
    }

    @Test
    fun containsTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(228)
        cache.put(1, "1")
        cache.put(3, "3")
        cache.put(5, "5")

        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.containsKey(2), false)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.containsKey(3), true)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.containsKey(4), false)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.containsKey(1), true)
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
    }

    @Test
    fun removeTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(228)
        cache.put(1, "1")
        cache.put(3, "3")
        cache.put(5, "5")

        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(5, "5"), Pair(3, "3"), Pair(1, "1")))
        assertEquals(cache.remove(5), "5")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(3, "3"), Pair(1, "1")))
    }

    @Test
    fun updateTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(228)
        cache.put(1, "1")
        cache.put(2, "2")
        cache.put(3, "3")

        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(3, "3"), Pair(2, "2"), Pair(1, "1")))
        assertEquals(cache.put(2, "new2"), "2")
        assertTrue(cache.iterator().asSequence().toList() == listOf(Pair(2, "new2"), Pair(3, "3"), Pair(1, "1")))
    }

    @Test
    fun updateOldValueTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(5)
        cache.put(1, "1")
        cache.put(2, "2")
        cache.put(3, "3")
        cache.put(4, "4")
        cache.put(5, "5")

        assertTrue(
            cache.iterator().asSequence().toList() ==
                    listOf(Pair(5, "5"), Pair(4, "4"), Pair(3, "3"), Pair(2, "2"), Pair(1, "1"))
        )
        assertEquals(cache.put(6, "6"), null)
        assertTrue(
            cache.iterator().asSequence().toList() ==
                    listOf(Pair(6, "6"), Pair(5, "5"), Pair(4, "4"), Pair(3, "3"), Pair(2, "2"))
        )
    }

    @Test
    fun mapperTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(100000)
        val mapper: MutableMap<Int, String> = mutableMapOf()
        val maxVal = 1000
        val maxN = 100
        for (num in 0..maxN) {
            val tryX = Random.nextInt(maxN)
            val tryVal = Random.nextInt(maxVal)
            when {
                tryX < 5 -> {
                    val resMapper = mapper.size
                    val resLRU = cache.getSize()
                    assertTrue(resMapper == resLRU)
                }
                tryX < 15 -> {
                    val resMapper = mapper.remove(tryVal)
                    val resLRU = cache.remove(tryVal)
                    assertTrue(resMapper == resLRU)
                }
                tryX < 60 -> {
                    val resMapper = mapper.put(tryVal, tryVal.toString())
                    val resLRU = cache.put(tryVal, tryVal.toString())
                    assertTrue(resMapper == resLRU)
                }
                else -> {
                    val resMapper = mapper.containsKey(tryVal)
                    val resLRU = cache.containsKey(tryVal)
                    assertTrue(resMapper == resLRU)
                }
            }
        }
    }

    @Test
    fun cachePutTest() {
        val cache: LRUCache<Int, String> = LRUCacheImpl(100)
        val maxN = 100000
        for (tryVal in 0..maxN) {
            val oldVal = tryVal - 100
            if (oldVal < 0) {
                cache.put(tryVal, tryVal.toString())
                assertTrue(cache.containsKey(tryVal))
            } else {
                assertTrue(cache.containsKey(oldVal))
                cache.put(tryVal, tryVal.toString())
                assertFalse(cache.containsKey(oldVal))
                assertTrue(cache.containsKey(tryVal))
            }
        }
    }

    @Test
    fun cacheGetTest() {
        val maxGetN = 100
        val cache: LRUCache<Int, String> = LRUCacheImpl(maxGetN + 1)
        for (tryVal in 0..maxGetN) {
            cache.put(tryVal, tryVal.toString())
        }
        val list = mutableListOf<Pair<Int, String>>()
        for (tryVal in maxGetN downTo 0) {
            list.add(Pair(tryVal, tryVal.toString()))
            assertTrue(cache.get(tryVal) == tryVal.toString())
        }
        list.reverse()
        assertTrue(list.toList() == cache.iterator().asSequence().toList())
    }

}
