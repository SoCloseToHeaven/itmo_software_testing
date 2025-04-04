package task2

import org.junit.jupiter.api.Test
import kotlin.test.assertFalse
import kotlin.test.assertTrue

class BTreeTest {

    @Test
    fun `test search in empty tree`() {
        val bTree = BTree()
        assertFalse(bTree.search(10))
    }

    @Test
    fun `test search existing key`() {
        val bTree = BTree()
        bTree.insert(10)
        assertTrue(bTree.search(10))
    }

    @Test
    fun `test search non-existing key`() {
        val bTree = BTree()
        bTree.insert(10)
        assertFalse(bTree.search(20))
    }

    @Test
    fun `test insert single key`() {
        val bTree = BTree()
        bTree.insert(10)
        assertTrue(bTree.search(10))
    }

    @Test
    fun `test insert multiple keys`() {
        val bTree = BTree()
        bTree.insert(10)
        bTree.insert(20)
        bTree.insert(5)
        assertTrue(bTree.search(10))
        assertTrue(bTree.search(20))
        assertTrue(bTree.search(5))
    }

    @Test
    fun `test delete existing key`() {
        val bTree = BTree()
        bTree.insert(10)
        bTree.delete(10)
        assertFalse(bTree.search(10))
    }

    @Test
    fun `test delete non-existing key`() {
        val bTree = BTree()
        bTree.insert(10)
        bTree.delete(20)
        assertTrue(bTree.search(10))
    }

    @Test
    fun `test delete from leaf node`() {
        val bTree = BTree()
        bTree.insert(10)
        bTree.insert(20)
        bTree.delete(10)
        assertFalse(bTree.search(10))
        assertTrue(bTree.search(20))
    }

    @Test
    fun `test delete from internal node with enough children`() {
        val bTree = BTree(2)
        for (i in 1..5) {
            bTree.insert(i)
        }
        bTree.delete(3)
        assertFalse(bTree.search(3))
        assertTrue(bTree.search(1))
        assertTrue(bTree.search(2))
        assertTrue(bTree.search(4))
        assertTrue(bTree.search(5))
    }

    @Test
    fun `test delete from internal node with not enough children`() {
        val bTree = BTree(2)
        for (i in 1..3) {
            bTree.insert(i)
        }
        bTree.delete(2)
        assertFalse(bTree.search(2))
        assertTrue(bTree.search(1))
        assertTrue(bTree.search(3))
    }

    @Test
    fun `test root node becomes leaf after delete`() {
        val bTree = BTree()
        bTree.insert(10)
        bTree.insert(20)
        bTree.delete(10)
        bTree.delete(20)
        assertTrue(bTree.root.isLeaf)
    }

    @Test
    fun `test delete from empty tree`() {
        val bTree = BTree()
        bTree.delete(10)
        assertFalse(bTree.search(10))
    }

    @Test
    fun `test delete from internal node`() {
        val bTree = BTree(2)
        bTree.insert(1)
        bTree.insert(2)
        bTree.insert(3)
        bTree.insert(4)
        bTree.insert(5)

        assertTrue(bTree.search(3))

        bTree.delete(3)

        assertFalse(bTree.search(3))

        assertTrue(bTree.search(1))
        assertTrue(bTree.search(2))
        assertTrue(bTree.search(4))
        assertTrue(bTree.search(5))
    }

    @Test
    fun `test delete from root with children after multiple inserts`() {
        val bTree = BTree(2)
        for (i in 1..10) {
            bTree.insert(i)
        }

        assertTrue(bTree.root.keys.isNotEmpty())
        assertTrue(bTree.root.children.isNotEmpty())

        bTree.delete(5)

        assertFalse(bTree.search(5))

        for (i in 1..10) {
            if (i != 5) {
                assertTrue(bTree.search(i))
            }
        }
    }

    @Test
    fun `test delete from root`() {
        val bTree = BTree(2)
        for (i in 1..10) {
            bTree.insert(i)
        }

        assertTrue(bTree.root.keys.isNotEmpty())
        assertTrue(bTree.root.children.isNotEmpty())

        bTree.delete(4)

        assertFalse(bTree.search(4))
    }

    @Test
    fun `test delete with merging children`() {
        val bTree = BTree(2)
        for (i in 1..20) {
            bTree.insert(i)
        }

        assertTrue(bTree.root.keys.isNotEmpty())
        assertTrue(bTree.root.children.isNotEmpty())

        bTree.delete(4)

        assertFalse(bTree.search(4))
    }
}
