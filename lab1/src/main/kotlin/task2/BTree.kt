package task2

class BTree(private val degree: Int) {
    private var root: BTreeNode? = null

    inner class BTreeNode(var isLeaf: Boolean) {
        val keys = mutableListOf<Int>()
        val children = mutableListOf<BTreeNode>()
    }

    fun search(key: Int): Boolean {
        return search(root, key)
    }

    private fun search(node: BTreeNode?, key: Int): Boolean {
        if (node == null) return false
        var i = 0
        while (i < node.keys.size && key > node.keys[i]) i++
        if (i < node.keys.size && key == node.keys[i]) return true
        return if (node.isLeaf) false else search(node.children[i], key)
    }

    fun insert(key: Int) {
        if (root == null) {
            root = BTreeNode(true)
            root!!.keys.add(key)
        } else {
            if (root!!.keys.size == 2 * degree - 1) {
                val newRoot = BTreeNode(false)
                newRoot.children.add(root!!)
                splitChild(newRoot, 0)
                root = newRoot
            }
            insertNonFull(root!!, key)
        }
    }

    private fun insertNonFull(node: BTreeNode, key: Int) {
        var i = node.keys.size - 1
        if (node.isLeaf) {
            node.keys.add(i + 1, key)
        } else {
            while (i >= 0 && key < node.keys[i]) i--
            i++
            if (node.children[i].keys.size == 2 * degree - 1) {
                splitChild(node, i)
                if (key > node.keys[i]) i++
            }
            insertNonFull(node.children[i], key)
        }
    }

    private fun splitChild(parent: BTreeNode, index: Int) {
        val child = parent.children[index]
        val newChild = BTreeNode(child.isLeaf)
        parent.keys.add(index, child.keys[degree - 1])
        parent.children.add(index + 1, newChild)
        newChild.keys.addAll(child.keys.subList(degree, 2 * degree - 1))
        child.keys.subList(degree - 1, 2 * degree - 1).clear()
        if (!child.isLeaf) {
            newChild.children.addAll(child.children.subList(degree, 2 * degree))
            child.children.subList(degree, 2 * degree).clear()
        }
    }

    fun delete(key: Int) {
        if (root == null) return
        delete(root!!, key)
        if (root!!.keys.isEmpty() && !root!!.isLeaf) {
            root = root!!.children[0]
        }
    }

    private fun delete(node: BTreeNode, key: Int) {
        val index = findKeyIndex(node, key)
        if (index < node.keys.size && node.keys[index] == key) {
            if (node.isLeaf) {
                node.keys.removeAt(index)
            } else {
                deleteFromNonLeaf(node, index)
            }
        } else {
            if (node.isLeaf) return
            val child = node.children[if (index == node.keys.size) index - 1 else index]
            if (child.keys.size < degree) {
                fill(node, index)
            }
            delete(child, key)
        }
    }

    private fun deleteFromNonLeaf(node: BTreeNode, index: Int) {
        val key = node.keys[index]
        if (node.children[index].keys.size >= degree) {
            val pred = getPredecessor(node, index)
            node.keys[index] = pred
            delete(node.children[index], pred)
        } else if (node.children[index + 1].keys.size >= degree) {
            val succ = getSuccessor(node, index)
            node.keys[index] = succ
            delete(node.children[index + 1], succ)
        } else {
            merge(node, index)
            delete(node.children[index], key)
        }
    }

    private fun findKeyIndex(node: BTreeNode, key: Int): Int {
        var i = 0
        while (i < node.keys.size && key > node.keys[i]) i++
        return i
    }

    private fun getPredecessor(node: BTreeNode, index: Int): Int {
        var current = node.children[index]
        while (!current.isLeaf) {
            current = current.children[current.keys.size]
        }
        return current.keys.last()
    }

    private fun getSuccessor(node: BTreeNode, index: Int): Int {
        var current = node.children[index + 1]
        while (!current.isLeaf) {
            current = current.children[0]
        }
        return current.keys.first()
    }

    private fun fill(node: BTreeNode, index: Int) {
        if (index != 0 && node.children[index - 1].keys.size >= degree) {
            borrowFromPrev(node, index)
        } else if (index != node.keys.size && node.children[index + 1].keys.size >= degree) {
            borrowFromNext(node, index)
        } else {
            if (index != node.keys.size) {
                merge(node, index)
            } else {
                merge(node, index - 1)
            }
        }
    }

    private fun borrowFromPrev(node: BTreeNode, index: Int) {
        val child = node.children[index]
        val sibling = node.children[index - 1]
        child.keys.add(0, node.keys[index - 1])
        node.keys[index - 1] = sibling.keys.last()
        if (!child.isLeaf) {
            child.children.add(0, sibling.children.last())
            sibling.children.removeAt(sibling.children.size - 1)
        }
        sibling.keys.removeAt(sibling.keys.size - 1)
    }

    private fun borrowFromNext(node: BTreeNode, index: Int) {
        val child = node.children[index]
        val sibling = node.children[index + 1]
        child.keys.add(node.keys[index])
        node.keys[index] = sibling.keys.first()
        if (!child.isLeaf) {
            child.children.add(sibling.children.first())
            sibling.children.removeAt(0)
        }
        sibling.keys.removeAt(0)
    }

    private fun merge(node: BTreeNode, index: Int) {
        val child = node.children[index]
        val sibling = node.children[index + 1]
        child.keys.add(node.keys[index])
        child.keys.addAll(sibling.keys)
        if (!child.isLeaf) {
            child.children.addAll(sibling.children)
        }
        node.keys.removeAt(index)
        node.children.removeAt(index + 1)
    }
}