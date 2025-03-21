package task2

class BTreeNode(var isLeaf: Boolean = true) {
    val keys = mutableListOf<Int>()
    val children = mutableListOf<BTreeNode>()
}