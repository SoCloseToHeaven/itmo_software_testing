package task2

import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertAll
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class BTreeTest {

    private lateinit var bTree: BTree

    companion object {
        private const val DEGREE = 3
        private val TEST_KEYS = listOf(10, 20, 30, 40, 50, 60, 70, 80, 90)
    }

    @BeforeEach
    fun setUp() {
        bTree = BTree(DEGREE)
    }

    @Test
    fun `insert and search should work correctly`() {
        TEST_KEYS.forEach { bTree.insert(it) }
        TEST_KEYS.forEach { key ->
            assertTrue(bTree.search(key), "Key $key should be found in the tree")
        }
        assertFalse(bTree.search(100), "Key 100 should not be found in the tree")
    }

    @Test
    fun `delete should remove keys correctly`() {
        TEST_KEYS.forEach { bTree.insert(it) }


        bTree.delete(20)
        bTree.delete(50)

        assertAll( "Delete elements",
            { assertFalse(bTree.search(20), "Key 20 should be deleted") },
            { assertFalse(bTree.search(50), "Key 50 should be deleted") }
        )

        listOf(10, 30, 40, 60, 70, 80, 90).forEach { key ->
            assertTrue(bTree.search(key), "Key $key should still be in the tree")
        }
    }

    @Test
    fun `delete from non-leaf node should work correctly`() {
        TEST_KEYS.forEach { bTree.insert(it) }
        bTree.delete(30)

        assertFalse(bTree.search(30), "Key 30 should be deleted")
        listOf(10, 20, 40, 50, 60, 70, 80, 90).forEach { key ->
            assertTrue(bTree.search(key), "Key $key should still be in the tree")
        }
    }

    @Test
    fun `tree should handle large number of keys correctly`() {
        val largeKeys = (1..100).toList()
        largeKeys.forEach { bTree.insert(it) }

        largeKeys.forEach { key ->
            assertTrue(bTree.search(key), "Key $key should be found in the tree")
        }

        largeKeys.filter { it % 2 == 0 }.forEach { bTree.delete(it) }

        largeKeys.filter { it % 2 == 0 }.forEach { key ->
            assertFalse(bTree.search(key), "Key $key should be deleted")
        }

        largeKeys.filter { it % 2 != 0 }.forEach { key ->
            assertTrue(bTree.search(key), "Key $key should still be in the tree")
        }
    }
}