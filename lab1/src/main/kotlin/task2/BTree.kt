package task2

class BTree(private val t: Int = 5) {
    var root: BTreeNode = BTreeNode(true)
        private set

    fun search(key: Int): Boolean = searchInNode(root, key)

    private fun searchInNode(node: BTreeNode?, key: Int): Boolean {
        node ?: return false
        var i = 0
        while (i < node.keys.size && key > node.keys[i]) i++
        return when {
            i < node.keys.size && key == node.keys[i] -> true
            node.isLeaf -> false
            else -> searchInNode(node.children[i], key)
        }
    }

    fun insert(key: Int) {
        if (root.keys.size == 2 * t - 1) {
            val newRoot = BTreeNode(false)
            newRoot.children.add(root)
            root = newRoot
            splitChild(newRoot, 0)
        }
        insertNonFull(root, key)
    }

    private fun splitChild(parent: BTreeNode, index: Int) {
        val child = parent.children[index]
        val newChild = BTreeNode(child.isLeaf)
        val middleIndex = t - 1

        newChild.keys.addAll(child.keys.subList(middleIndex + 1, child.keys.size))
        child.keys.subList(middleIndex + 1, child.keys.size).clear()

        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(middleIndex + 1, child.children.size))
            child.children.subList(middleIndex + 1, child.children.size).clear()
        }

        parent.keys.add(index, child.keys.removeAt(middleIndex))
        parent.children.add(index + 1, newChild)
    }

    private fun insertNonFull(node: BTreeNode, key: Int) {
        var i = node.keys.lastIndex
        when {
            node.isLeaf -> {
                while (i >= 0 && key < node.keys[i]) i--
                node.keys.add(i + 1, key)
            }
            else -> {
                while (i >= 0 && key < node.keys[i]) i--
                i++
                if (node.children[i].keys.size == 2 * t - 1) {
                    splitChild(node, i)
                    if (key > node.keys[i]) i++
                }
                insertNonFull(node.children[i], key)
            }
        }
    }

    fun delete(key: Int) {
        deleteFromNode(root, key)
        if (root.keys.isEmpty() && !root.isLeaf) {
            root = root.children.first()
        }
    }

    private fun deleteFromNode(node: BTreeNode, key: Int): Boolean {
        val index = node.keys.indexOfFirst { it >= key }
        val isPresent = index < node.keys.size && node.keys[index] == key

        return when {
            node.isLeaf -> node.keys.remove(key)
            isPresent -> deleteInternalNode(node, index)
            else -> deleteFromChild(node, index)
        }
    }

    private fun deleteInternalNode(node: BTreeNode, index: Int): Boolean {
        val child = node.children[index]
        return if (child.keys.size >= t) {
            node.keys[index] = deletePredecessor(child)
            true
        } else {
            val rightChild = node.children[index + 1]
            if (rightChild.keys.size >= t) {
                node.keys[index] = deleteSuccessor(child)
                true
            } else {
                mergeNodes(node, index)
                deleteFromNode(child, node.keys[index])
            }
        }
    }

    private fun deletePredecessor(node: BTreeNode): Int {
        return if (node.isLeaf) node.keys.removeLast()
        else deletePredecessor(node.children.last())
    }

    private fun deleteSuccessor(node: BTreeNode): Int {
        return if (node.isLeaf) node.keys.removeFirst()
        else deleteSuccessor(node.children.first())
    }

    private fun deleteFromChild(parent: BTreeNode, index: Int): Boolean {
        val child = parent.children[index]
        if (child.keys.size < t) {
            when {
                index > 0 && parent.children[index - 1].keys.size >= t -> {
                    borrowFromLeft(parent, index)
                }
                index < parent.children.lastIndex && parent.children[index + 1].keys.size >= t -> {
                    borrowFromRight(parent, index)
                }
                else -> {
                    mergeNodes(parent, if (index > 0) index - 1 else index)
                }
            }
        }
        return deleteFromNode(child, parent.keys.getOrNull(index) ?: Int.MAX_VALUE)
    }

    private fun borrowFromLeft(parent: BTreeNode, index: Int) {
        val child = parent.children[index]
        val sibling = parent.children[index - 1]

        child.keys.add(0, parent.keys[index - 1])
        parent.keys[index - 1] = sibling.keys.removeLast()

        if (!child.isLeaf) {
            child.children.add(0, sibling.children.removeLast())
        }
    }

    private fun borrowFromRight(parent: BTreeNode, index: Int) {
        val child = parent.children[index]
        val sibling = parent.children[index + 1]

        child.keys.add(parent.keys[index])
        parent.keys[index] = sibling.keys.removeFirst()

        if (!child.isLeaf) {
            child.children.add(sibling.children.removeFirst())
        }
    }

    private fun mergeNodes(parent: BTreeNode, index: Int) {
        val left = parent.children[index]
        val right = parent.children.removeAt(index + 1)

        left.keys.add(parent.keys.removeAt(index))
        left.keys.addAll(right.keys)
        left.children.addAll(right.children)
    }
}
