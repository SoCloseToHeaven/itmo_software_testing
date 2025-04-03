package task2

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class BTreeTest {
    private lateinit var bTree: BTree

    @BeforeEach
    fun setUp() {
        bTree = BTree(2) // Минимальный порядок B-дерева
    }

    @Test
    fun testInsertAndSearch() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)

        assertTrue(bTree.search(10))
        assertTrue(bTree.search(20))
        assertTrue(bTree.search(5))
        assertFalse(bTree.search(15))
    }

    @Test
    fun testInsertAndSplit() {
        // Вставляем 5 элементов, чтобы вызвать разделение
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)
        bTree.insert(6)
        bTree.insert(12)

        assertTrue(bTree.search(6))
        assertTrue(bTree.search(12))
        assertFalse(bTree.search(15))
    }

    @Test
    fun testDeleteLeafNode() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)

        bTree.delete(5)
        assertFalse(bTree.search(5))
    }

    @Test
    fun testDeleteInternalNode() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)
        bTree.insert(6)
        bTree.insert(12)

        bTree.delete(10)
        assertFalse(bTree.search(10))
        assertTrue(bTree.search(20))
    }

    @Test
    fun testDeleteRootNode() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)

        bTree.delete(10)
        assertFalse(bTree.search(10))
        assertTrue(bTree.search(5))
        assertTrue(bTree.search(20))
    }

    @Test
    fun testDeleteNonExistentNode() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)

        bTree.delete(15) // Удаляем несуществующий элемент
        assertTrue(bTree.search(10))
        assertTrue(bTree.search(20))
        assertTrue(bTree.search(5))
    }

    @Test
    fun testDeleteAndMerge() {
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)
        bTree.insert(6)
        bTree.insert(12)

        bTree.delete(20)
        assertFalse(bTree.search(20))
        assertTrue(bTree.search(10))
        assertTrue(bTree.search(5))
        assertTrue(bTree.search(6))
        assertTrue(bTree.search(12))
    }
}
