package task2

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test
import kotlin.test.assertTrue
import kotlin.test.assertFalse

class BTreeTest {
    @Test
    fun testInsertAndStructure() {
        val tree = BTree(t = 5)
        val data = (1..5).toList()
        data.forEach(tree::insert)

        assertEquals(data.sorted(), tree.root.keys)
        assertTrue(tree.root.isLeaf)
    }

    @Test
    fun testSearch() {
        val tree = BTree(t = 5)
        listOf(2, 4, 6, 8, 10).forEach(tree::insert)

        assertTrue(tree.search(4))
        assertTrue(tree.search(10))

        assertFalse(tree.search(5))
        assertFalse(tree.search(11))
    }

    @Test
    fun testDelete() {
        val tree = BTree(t = 5)
        listOf(1, 2, 3, 4, 5).forEach(tree::insert)

        tree.delete(3)
        assertEquals(listOf(1, 2, 4, 5), tree.root.keys)

        tree.delete(5)
        assertEquals(listOf(1, 2, 4), tree.root.keys)
    }

    @Test
    fun testSplitBehavior() {
        val tree = BTree(t = 5)
        repeat(9) { tree.insert(it) }
        assertEquals(9, tree.root.keys.size)

        tree.insert(9)
        assertEquals(1, tree.root.keys.size) // Средний элемент (4)
        assertEquals(2, tree.root.children.size)

        val left = tree.root.children[0]
        val right = tree.root.children[1]

        assertEquals((0..3).toList(), left.keys)  // 4 элемента
        assertEquals((5..9).toList(), right.keys) // 5 элементов
    }

    @Test
    fun testNodeConstraints() {
        val tree = BTree(t = 5)
        repeat(20) { tree.insert(it) }

        fun validateNode(node: BTreeNode) {
            when {
                node == tree.root -> assertTrue(node.keys.size in 1..9)
                node.isLeaf -> assertTrue(node.keys.size in 4..9)
                else -> assertTrue(node.keys.size in 4..9)
            }

            if (!node.isLeaf) {
                assertEquals(node.keys.size + 1, node.children.size)
                node.children.forEach(::validateNode)
            }
        }

        validateNode(tree.root)
    }
}
